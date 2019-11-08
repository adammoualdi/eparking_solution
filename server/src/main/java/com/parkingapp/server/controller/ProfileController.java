package com.parkingapp.server.controller;

import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.ProfileDTO;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProfileController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired UserInfoRepo userInfoRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ResponseEntity<ProfileDTO> test(@RequestHeader("Authorization") String token) throws Exception {
        // remove 'Bearer ' from Authorization header
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userInfoRepo.findByUsername(username);

        // Use profile DTO to pass information to frontend.
        ProfileDTO profileInfo = new ProfileDTO();
        profileInfo.setUsername(user.getUsername());
        profileInfo.setFirstname(user.getFirstname());
        profileInfo.setLastname(user.getLastname());
        profileInfo.setEmail(user.getEmail());
        profileInfo.setCars(user.getCars());
	    return ResponseEntity.ok(profileInfo);
	}
}