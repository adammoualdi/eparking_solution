package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MainController {

	@Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;

	// @Autowired UserInfoRepo userInfoRepo;

    // @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test() throws Exception {
		UserInfo user = userRepo.findByUsername("owneruser");
		// Set<Location> loc = locationRepo.findByPermissions(user);
		// Location l = loc.iterator().next();
		ArrayList<Location> l = locationRepo.findByPermissions(user);
		ArrayList<UserInfo> u = userRepo.findByLocationsPermission(l.get(0));
	    return ResponseEntity.ok(u.get(0));
	}
}