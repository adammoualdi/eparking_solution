package com.parkingapp.server.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    
    public boolean checkAvailability(LocalDateTime arrivingTime, LocalDateTime leavingTime, LocationDTO locationDto) {
        return true;
    }

    @Scheduled(fixedRate = 30000, cron = "0 0 0 * * ?")
    public void checkCurrentUserBookings() {

    }
	
}