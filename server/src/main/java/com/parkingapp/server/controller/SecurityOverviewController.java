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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SecurityOverviewController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('SECURITY')")
	@RequestMapping(value = "/security/bookings", method = RequestMethod.POST)
    public ResponseEntity<?> getBookings(@RequestHeader("Authorization") String token, 
                                        @RequestBody LocationsDistanceDTO clientDistances) throws Exception {
        System.out.println("TEST---------------------------------------------------------------------");
        System.out.println(clientDistances.toString());
        // LocationsDistanceDTO tmp = clientDistances;
        System.out.println(clientDistances.getArriveTime());
        System.out.println(clientDistances.getLeavingTime());
        System.out.println("GET BOOKINGS FOR THAT LOCATION");

        System.out.println("Amount of locations: " + clientDistances.getLocations().size());
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        for (int i = 0; i < clientDistances.getLocations().size(); i++) {
            Location loc = locationRepo.findByLocationId(clientDistances.getLocations().get(i).getLocationId());
            bookings = bookingRepo.findByLocationId(loc);
            System.out.println("SIZE OF BOOKING ARRAY " + bookings.size());
        }

        // LocationsDistanceDTO output = new LocationsDistanceDTO();
        // output.setLocations(locationsArray);

        return ResponseEntity.ok(bookings);
        }
    
}