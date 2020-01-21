package com.parkingapp.server.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;

import com.parkingapp.server.EParkingApplication;
import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.LocationDTO;

@Service
public class BookingCheckerService {
	 
    @Autowired LocationRepo locationRepo;
    @Autowired BookingRepository bookingRepo;
    
    boolean checkAvailability(LocalDateTime arriveTime, LocalDateTime leavingTime, LocationDTO locationDto) {
        // Get all location information to get booking info for that location
        Location loc = locationRepo.findByLocationId(locationDto.getLocationId());
        System.out.println("GET BOOKINGS FOR THAT LOCATION");
        // ArrayList<Booking> bookings = bookingRepo.findAvailableLocation(loc.getLocationId(), arriveTime);
        // System.out.println("SIZE OF BOOKING ARRAY " + bookings.size());
        // check if the bookings are the same
        // for (int i = 0; i < bookings.size(); i++) {
            // if (arriveTime.isAfter(bookings.get(i).getStartDate())) {

            // }
        // }
        return true;
    }
	
}