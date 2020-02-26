package com.parkingapp.server.controller;

import java.util.ArrayList;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.DashboardLocationDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.LocationsDistanceDTO;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OwnerDashboardController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('OWNER')")
	@RequestMapping(value = "/locations/owners", method = RequestMethod.GET)
	public ResponseEntity<DashboardLocationsDTO> ownerLocations(@RequestHeader("Authorization") String token) throws Exception {
                
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        ArrayList<Location> loc = locationRepo.findByUserId(user);
        
        // Be able to store loc values into new list with DTO object.
        ArrayList<DashboardLocationDTO> tmpLocations = new ArrayList<>();
        // loop through database values and assign them to DTO (DashboardLocationDTO).
        for (int i = 0; i < loc.size(); i++) {
            DashboardLocationDTO location = new DashboardLocationDTO();
            Location temp = new Location();
            temp = loc.get(i);
            // check if location has been approved
            if (temp.getApproved() == true) {
                location.setId(temp.getLocationId());
                location.setAddress1(temp.getAddress1());
                location.setAddress2(temp.getAddress2());
                location.setCity(temp.getCity());
                location.setCountry(temp.getCountry());
                location.setPostcode(temp.getPostcode());
                location.setLatitude(temp.getLatitude());
                location.setLongitude(temp.getLongitude());
                // add DTO to temp array of that DTO object - to be returned.
                tmpLocations.add(location);
            }
        }

        for (int ii = 0; ii < tmpLocations.size(); ii++) {
            System.out.println(tmpLocations.get(ii).toString());
        }

        // The DTO that's passed to front-end via JSON.
        DashboardLocationsDTO locations = new DashboardLocationsDTO();
        locations.setLocations(tmpLocations);
        
	    return ResponseEntity.ok(locations);
    }

    @PreAuthorize("hasRole('OWNER')")
	@RequestMapping(value = "/locations/add", method = RequestMethod.POST)
    public ResponseEntity<?> addParkingLocation(@RequestHeader("Authorization") String token, 
                                                @RequestBody Location location) throws Exception {
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        location.setUserId(user);
        System.out.println(location.isSensors());
        System.out.println(location.toString());
        locationRepo.save(location);
	    return ResponseEntity.ok(location);
    }


    
}