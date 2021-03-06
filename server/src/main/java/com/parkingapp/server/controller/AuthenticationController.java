package com.parkingapp.server.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.ChangePasswordDTO;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired UserInfoRepo userInfoRepo;

	@Autowired BCryptPasswordEncoder pe;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserInfo authenticationRequest) throws Exception {

		// Check that username and password exists
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		// Get user infomation
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		System.out.println("ROLE " + userDetails.getAuthorities());

		// Generate JWT token with user information
		final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println(authenticationRequest.toString());
		UserInfo user = userInfoRepo.findByUsername(authenticationRequest.getUsername());
		BigDecimal bd = new BigDecimal(user.getDeposit()).setScale(3, RoundingMode.HALF_UP);
		return ResponseEntity.ok(new JwtResponse(token, user.getUsername(), user.getRole().getRole(), user.isDefaultPassword(), bd.doubleValue()));
	}
	
	private void authenticate(String username, String password) throws Exception {
		System.out.println("-------------------TRY");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			System.out.println("-------------------OUT AUTH");
		} catch (DisabledException e) {
			System.out.println("-------------------TEST");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			System.out.println("-------------------INVALID");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@RequestMapping(value = "/authenticate/changepassword", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO request,
											@RequestHeader("Authorization") String token)  {
		
		// Get user info from token
		String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
		UserInfo user = userInfoRepo.findByUsername(usernameTok);

		user.setPassword(pe.encode(request.getNewPassword()));
		user.setDefaultPassword(false);
		
		try {
			userInfoRepo.save(user);
		} catch(Exception e) {
			System.out.println("ERROR CHANGING PASSWORD");
		}

		// new token not needed as password isn't included in token

		return ResponseEntity.ok(request);
	}
}