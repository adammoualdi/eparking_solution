package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.ChangePasswordDTO;
import com.parkingapp.server.repository.RoleRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.EmailServicer;
import com.parkingapp.server.services.ErrorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin
public class RegisterController extends EmailServicer {
	
	@Autowired UserInfoRepo userInfoRepo;
	@Autowired RoleRepo roleRepo;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired 
	ErrorService errorService;
	
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new RegisterValidator());
    }

	// @CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody UserInfo user, BindingResult result )  {
		boolean error = false;
		System.out.println(user.toString());
		System.out.println(user.getRoleName());

		// Randomly set. Email is sent to user with password. This will be changed when they log in
		if (user.getRoleName().equals("SECURITY")) {
			// Get random password
			String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        	StringBuilder salt = new StringBuilder();
        	Random rnd = new Random();
        	while (salt.length() < 12) { // length of the random string.
            	int index = (int) (rnd.nextFloat() * SALTCHARS.length());
				salt.append(SALTCHARS.charAt(index));
			}
			user.setPassword(salt.toString());
			user.setPassword2(salt.toString());
			// set to true when default created password hasn't been changed
			user.setDefaultPassword(true);
		}
		try {
			sendEmail(user.getUsername(), "adammoualdi@hotmail.co.uk", user.getPassword());
		} catch(Exception e) {
			System.out.println(e);
		}
		// Encrypt password
		BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
		user.setPassword(pe.encode(user.getPassword()));
		System.out.println(pe.encode(user.getPassword()));
		// check validation errors
		if (result.hasErrors()) { 
			ErrorResponse er = errorService.createErrorResponse(result, "Validation error");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
		
		if (user.getRoleName().equals("SECURITY")) {
			user.setRole(roleRepo.findByRole("SECURITY"));
		}
		else if(user.getRoleName().equals("OWNER")) {
			user.setRole(roleRepo.findByRole("OWNER"));
		}
		else {
			//set Role to USER
	    	String userRole = "USER";
		    user.setRole(roleRepo.findByRole(userRole));
			System.out.println(user.toString());
		}
		try{
			userInfoRepo.save(user);
	    }
	    catch(EntityExistsException e){
	    	error = true;
	    }
	    catch(NullPointerException e) {
	    	error = true;
	    }
		if (error == false) { 
			System.out.println("User saved");
			System.out.println(user.toString());
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} else {
			System.out.println("User not saved");
			return ResponseEntity.ok(user);
		}
	}
}
