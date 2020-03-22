package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.ChangePasswordDTO;
import com.parkingapp.server.domain.DTO.RegisterDTO;
import com.parkingapp.server.repository.CarRepository;
import com.parkingapp.server.repository.LocationRepo;
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
	@Autowired CarRepository carRepo;
	@Autowired LocationRepo locRepo;
	
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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO userDTO, BindingResult result )  {
		boolean error = false;
		System.out.println(userDTO.toString());
		System.out.println(userDTO.getRoleName());

		UserInfo user = new UserInfo();
		Car userCar = new Car();
		userCar.setUserId(user);
		userCar.setRegNo(userDTO.getCarreg());
		user.setEmail(userDTO.getEmail());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setDofb(userDTO.getDofb());
		user.setUsername(userDTO.getUsername());

		// Randomly set. Email is sent to user with password. This will be changed when they log in
		if (userDTO.getRoleName().equals("SECURITY")) {
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
			userDTO.setPassword(salt.toString());
			userDTO.setPassword2(salt.toString());
			// set to true when default created password hasn't been changed
			user.setDefaultPassword(true);

			user.setUsername((userDTO.getFirstname() + '.' + userDTO.getLastname()).toLowerCase());
			int nameID = 0;
			while (true) {
				UserInfo usernameExists = userInfoRepo.findByUsername(user.getUsername());
				if (usernameExists == null) {
					break;
				}
				nameID += 1;
				user.setUsername((userDTO.getFirstname() + '.' + userDTO.getLastname() + nameID).toLowerCase());
			}

			// Create new set for new security user
			System.out.println("ADD LOCATION");
			System.out.println(userDTO.getLocationId());
			Location loc = locRepo.findByLocationId(userDTO.getLocationId());
			Set<Location> locSet = new HashSet<Location>();
			locSet.add(loc);
			user.setLocationsPermission(locSet);

			try {
				sendEmail(user.getUsername(), userDTO.getEmail(), userDTO.getPassword());
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		// Encrypt password
		BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();
		user.setPassword(pe.encode(userDTO.getPassword()));
		System.out.println(pe.encode(userDTO.getPassword()));
		// check validation errors
		if (result.hasErrors()) { 
			ErrorResponse er = errorService.createErrorResponse(result, "Validation error");
			return new ResponseEntity<>(er, HttpStatus.OK);
		}
		
		if (userDTO.getRoleName().equals("SECURITY")) {
			user.setRole(roleRepo.findByRole("SECURITY"));
		}
		else if(userDTO.getRoleName().equals("OWNER")) {
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
			carRepo.save(userCar);
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
