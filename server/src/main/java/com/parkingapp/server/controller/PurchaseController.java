package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.DashboardLocationDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.LocationsDistanceDTO;
import com.parkingapp.server.domain.DTO.PurchasePointsDTO;
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
public class PurchaseController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired UserInfoRepo userRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/dashboard/purchase", method = RequestMethod.POST)
    public ResponseEntity<?> buyPoints(@RequestHeader("Authorization") String token,
                                       @RequestBody PurchasePointsDTO deposit) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        
        user.setDeposit(user.getDeposit() + deposit.getDeposit());
        userRepo.save(user);
        deposit.setDeposit(user.getDeposit());
        return ResponseEntity.ok(deposit);
    }
    
}