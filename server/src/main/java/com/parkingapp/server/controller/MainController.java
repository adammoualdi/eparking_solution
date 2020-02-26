package com.parkingapp.server.controller;

import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.UserInfo;
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

	// @Autowired UserInfoRepo userInfoRepo;

    // @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test() throws Exception {
	    return ResponseEntity.ok("success");
	}
}