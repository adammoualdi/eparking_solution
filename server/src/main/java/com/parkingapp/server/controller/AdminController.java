package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.databind.node.TextNode;
import com.parkingapp.server.EParkingApplication;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.AdminLocationDTO;
import com.parkingapp.server.domain.DTO.AdminLocationsDTO;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.SecurityOverviewDTO;
import com.parkingapp.server.domain.DTO.UserDTO;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.EmailServicer;
import com.parkingapp.server.services.ErrorService;
import com.parkingapp.server.services.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AdminController extends EmailServicer {
    @Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired UserInfoRepo userInfoRepo;
	@Autowired LocationRepo locationRepo;
	
	@Autowired ErrorService errorService;

	// Receives all requests that need to be approved.
    @PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/locations/admin", method = RequestMethod.GET)
	public ResponseEntity<?> getLocationsForApproval(@RequestHeader("Authorization") String token) throws Exception {
		ArrayList<Location> loc = locationRepo.findByApproved(false);

        // Be able to store loc values into new list with DTO object.
        ArrayList<AdminLocationDTO> tmpLocations = new ArrayList<>();

		for (int i = 0; i < loc.size(); i++) {
			AdminLocationDTO location = new AdminLocationDTO();
			UserDTO user = new UserDTO();
            Location temp = new Location();
            temp = loc.get(i);
			location.setId(temp.getLocationId());
            location.setAddress1(temp.getAddress1());
			location.setAddress2(temp.getAddress2());
            location.setCity(temp.getCity());
            location.setCountry(temp.getCountry());
            location.setPostcode(temp.getPostcode());
            location.setLatitude(temp.getLatitude());
			location.setLongitude(temp.getLongitude());
			// location.setUserId(temp.getUserId());
			Iterator<UserInfo> it = temp.getPermissions().iterator();
     		while(it.hasNext()){
				UserInfo tmpUser = it.next();
				if (tmpUser.getRole().getRole().equals("Owner")) {
					user.setUsername(tmpUser.getUsername());
					user.setFirstname(tmpUser.getFirstname());
					user.setLastname(tmpUser.getLastname());
					user.setEmail(tmpUser.getEmail());
					user.setId(tmpUser.getId());
					user.setDofb(tmpUser.getDofb());
					location.setUserId(user);
					break;
				}
			 }
			System.out.println("OUTSIDE WHILE");
			location.setSpaces(temp.getSpaces());
			location.setWarning(false);
			location.setApproved(false);
			
			// Check if there's any conflicts with locations with current locations
			// This will let the admin know that an automatic check has found a potential issue
			ArrayList<Location> closeLocations = locationRepo.findByPostcode(location.getPostcode());
			for (int ii = 0; ii < closeLocations.size(); ii++) {
				if (location.getLongitude() == closeLocations.get(ii).getLongitude() &&
					location.getLatitude() == closeLocations.get(ii).getLatitude() &&
					location.getId() != closeLocations.get(ii).getLocationId()) {
						location.setWarning(true);
					}
			}			
            // add DTO to temp array of that DTO object - to be returned.
            tmpLocations.add(location);
		}
		
		// The DTO that's passed to front-end via JSON.
        AdminLocationsDTO locations = new AdminLocationsDTO();
        locations.setLocations(tmpLocations);

	    return ResponseEntity.ok(locations);
	}

    @PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/locations/approve", method = RequestMethod.POST)
	public ResponseEntity<?> approveLocation(@RequestHeader("Authorization") String token,
                                             @RequestBody AdminLocationDTO location) throws Exception {
		
		Location loc = locationRepo.findByLocationId(location.getId());
		UserInfo user = null;
		List<UserInfo> users = userInfoRepo.findByLocationsPermission(loc);
		System.out.println(users.size());
		for(int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getUsername());
			if (users.get(i).getRole().getId() == EParkingApplication.OWNER) {
				user = users.get(i);
			}

		}
		// Approved location - can now be used by system.
		if (location.isApproved()) {
			loc.setApproved(true);
			locationRepo.save(loc);
		}
		else {
			// loc.setApproved(false);
			// SEND EMAIL
			System.out.println("REMOVE LOCATION");
			user.getLocationsPermission().remove(loc);
			// user.setLocationsPermission(user.getLocationsPermission().remove(loc));
			userInfoRepo.save(user);
			System.out.println("SEND EMAIL");
			locationRejection(user.getEmail(), location.getMessage());
			System.out.println("DELETE LOCATION");
			locationRepo.delete(loc);
		}	
	    return ResponseEntity.ok(loc.getApproved());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/locations/security", method = RequestMethod.POST)
	public ResponseEntity<?> getSecurityUserInfo(@RequestHeader("Authorization") String token,
												 @RequestBody UserDTO username) throws Exception {
	
		UserInfo securityUser;
		securityUser = userInfoRepo.findByUsername(username.getUsername());
		System.out.println("username " + username.getUsername());
		// System.out.println(securityUser.getRole().getRole());
		if (securityUser == null || !securityUser.getRole().getRole().equals("Security")) {
			ErrorDTO error = new ErrorDTO("Input", "Security user doesn't exist");
			ErrorResponse er = errorService.createErrorResp(error, "Security user doesn't exist");
			return ResponseEntity.ok(er);
		}
		
		ArrayList<BookingDTO> locations = new ArrayList<BookingDTO>();

		Set<Location> userLocs = securityUser.getLocationsPermission();
		System.out.println("SIZE " + userLocs.size());
		ArrayList<BookingDTO> listDTO = new ArrayList<BookingDTO>();
		Iterator<Location> it = userLocs.iterator();
        // For each of the security user's locations
        while(it.hasNext()) {
            Location loc = it.next();
			// Put information into DTOs	
			BookingDTO bookingDTO = new BookingDTO();
			// UserDTO userDTO = new UserDTO();
			bookingDTO.setFirstname(securityUser.getFirstname());
			bookingDTO.setLastname(securityUser.getLastname());
			bookingDTO.setEmail(securityUser.getEmail());
			bookingDTO.setUsername(securityUser.getUsername());
			bookingDTO.setId(securityUser.getId());
			LocationDTO locDTO = new LocationDTO();
			locDTO.setAddress1(loc.getAddress1());
			locDTO.setAddress2(loc.getAddress2());
			locDTO.setCity(loc.getCity());
			locDTO.setCountry(loc.getCountry());
			locDTO.setPostcode(loc.getPostcode());
			locDTO.setLongitude(loc.getLongitude());
			locDTO.setLatitude(loc.getLatitude());
			locDTO.setLocationId(loc.getLocationId());
			bookingDTO.setLocationId(locDTO);
			listDTO.add(bookingDTO);       
		}
		locations.addAll(listDTO);
		
		SecurityOverviewDTO output = new SecurityOverviewDTO(locations);
	    return ResponseEntity.ok(output);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/locations/security/remove/{locationId}", method = RequestMethod.POST)
	public ResponseEntity<?> removeLocationFromSecurity(@RequestHeader("Authorization") String token,
														@PathVariable("locationId") int locationId,
												        @RequestBody UserDTO username) throws Exception {
	
		UserInfo securityUser;
		securityUser = userInfoRepo.findByUsername(username.getUsername());
		System.out.println("username " + username.getUsername());
		System.out.println(securityUser.getRole().getRole());
		if (securityUser == null || !securityUser.getRole().getRole().equals("Security")) {
			ErrorDTO error = new ErrorDTO("Input", "Security user doesn't exist");
			ErrorResponse er = errorService.createErrorResp(error, "Security user doesn't exist");
			return ResponseEntity.ok(er);
		}
		
		Location loc = locationRepo.findByLocationId(locationId);
		securityUser.getLocationsPermission().remove(loc);
		userInfoRepo.save(securityUser);
		
		// SecurityOverviewDTO output = new SecurityOverviewDTO(locations);
	    return ResponseEntity.ok("success");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/locations/security/add/{locationId}", method = RequestMethod.POST)
	public ResponseEntity<?> addLocationFromSecurity(@RequestHeader("Authorization") String token,
														@PathVariable("locationId") int locationId,
												        @RequestBody UserDTO username) throws Exception {
	
		UserInfo securityUser;
		securityUser = userInfoRepo.findByUsername(username.getUsername());
		System.out.println("username " + username.getUsername());
		System.out.println(securityUser.getRole().getRole());
		if (securityUser == null || !securityUser.getRole().getRole().equals("Security")) {
			ErrorDTO error = new ErrorDTO("Input", "Security user doesn't exist");
			ErrorResponse er = errorService.createErrorResp(error, "Security user doesn't exist");
			return ResponseEntity.ok(er);
		}
		
		Location loc = locationRepo.findByLocationId(locationId);
		securityUser.getLocationsPermission().add(loc);
		userInfoRepo.save(securityUser);
		
		// SecurityOverviewDTO output = new SecurityOverviewDTO(locations);
	    return ResponseEntity.ok("success");
	}
}