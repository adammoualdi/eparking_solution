package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.AdminLocationDTO;
import com.parkingapp.server.domain.DTO.AdminLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.UserDTO;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired UserInfoRepo userInfoRepo;
	@Autowired LocationRepo locationRepo;

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
		
		// Approved location - can now be used by system.
		if (location.isApproved()) {
			loc.setApproved(true);
		}
		else {
			loc.setApproved(false);
		}
		locationRepo.save(loc);
	    return ResponseEntity.ok(loc.getApproved());
	}
}