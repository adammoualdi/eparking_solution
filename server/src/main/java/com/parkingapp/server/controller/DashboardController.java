package com.parkingapp.server.controller;

import java.security.Principal;
import java.util.ArrayList;

import javax.persistence.EntityExistsException;

import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.Car;
import com.parkingapp.server.repository.CarRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DashboardController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired CarRepository carRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ResponseEntity<?> dashboard(@RequestHeader("Authorization") String token) throws Exception {
        ArrayList<Location> loc = locationRepo.findByCountry("country");
        for (int i = 0; i < loc.size(); i++) {
            System.out.println(loc.get(i));
        }

        Car car = new Car();
        System.out.println("TOKEN: " + token.substring(7,token.length()));
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        System.out.println("Username: " + username);
        car.setUserId(userRepo.findByUsername(username));
        car.setRegNo("regNo");
        car.setModel("MODEL");
        try{
	        //Try to insert your entity by calling persist method    
		    // save car to repo 
			System.out.println("Saving car to DB");
			carRepo.save(car);
	    }
	    catch(EntityExistsException e){
	        //Entity you are trying to insert already exist, then call merge method
            // error = true;
            System.out.println("CAR NOT SAVED");
	    }
        
	    return ResponseEntity.ok("success");
	}
}