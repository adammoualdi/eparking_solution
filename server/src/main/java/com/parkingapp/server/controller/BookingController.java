package com.parkingapp.server.controller;

import java.util.ArrayList;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
public class BookingController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
    public ResponseEntity<?> availableParkingLocations(
                                                       @RequestHeader("Authorization") String token, 
                                                       @RequestBody BookingDTO bookingDetails) throws Exception {
        System.out.println("TEST---------------------------------------------------------------------");
        System.out.println(bookingDetails.toString());

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        System.out.println(bookingDetails.getLocationId().getLocationId());
        Location loc = locationRepo.findByLocationId(bookingDetails.getLocationId().getLocationId());                                            
        Booking booking = new Booking();
		// booking.setId(bookingDetails.get);
		booking.setLocationId(loc);
		booking.setUserId(user);
        booking.setStartDate(bookingDetails.getStartDate());
        booking.setEndDate(bookingDetails.getEndDate());
		booking.setActive(true);
        try {
        bookingRepo.save(booking);
        } catch(Exception e) {
            System.out.println("ERROR-------------------------");
        }
	    return ResponseEntity.ok(bookingDetails);
    }


    
}