package com.parkingapp.server.controller;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
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
import org.springframework.http.HttpStatus;
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
public class ConfirmationController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/booking/confirm/{url}", method = RequestMethod.POST)
    public ResponseEntity<?> saveBooking(@RequestHeader("Authorization") String token,
                                         @PathVariable("url") String url) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        
        Booking booking = bookingRepo.findByBookingUrl(url);
        if (booking == null) {
            ErrorResponse er = new ErrorResponse(HttpStatus.BAD_REQUEST, "Error state", new ErrorDTO("booking", "Error finding booking"));
            return ResponseEntity.ok(er);
        } else {
            booking.setParkingConfirmation(true);
        }
	    return ResponseEntity.ok("success");
    }


    
}