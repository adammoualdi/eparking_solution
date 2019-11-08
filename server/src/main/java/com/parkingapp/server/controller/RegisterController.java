package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.repository.RoleRepo;
import com.parkingapp.server.repository.UserInfoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin
public class RegisterController {
	
	@Autowired UserInfoRepo userInfoRepo;
	@Autowired RoleRepo roleRepo;
	
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new RegisterValidator());
    }
	
	BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();

	// @CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody UserInfo user, BindingResult result )  {
		boolean error = false;
		System.out.println(user.toString());
		// Encrypt password
		user.setPassword(pe.encode(user.getPassword()));
		System.out.println(pe.encode(user.getPassword()));
		// check validation errors
		if (result.hasErrors()) { 
			System.out.println("--------------------------ERRORS");
			List<ErrorDTO> errorList = new ArrayList<ErrorDTO>();
			System.out.println( "---------------------------------------------" + result.getAllErrors().size());
			for (int i = 0; i < result.getAllErrors().size(); i++) {
				System.out.println( result.getAllErrors().get(i));
				ObjectError e = result.getAllErrors().get(i);
				FieldError fieldError = (FieldError) e;
				String field = fieldError.getField();
				System.out.println("----------------- " + field);
				errorList.add(new ErrorDTO(field, e.getDefaultMessage()));
				System.out.println("----------------- " + errorList);
			}
//			return ResponseEntity.ok(new ErrorResponse("404", "Validation failure", errorList));
//			return new ResponseEntity<ErrorResponse>(errorList, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK, "Validation failure", errorList), HttpStatus.OK);
//			return new ResponseEntity<UserInfo>(user, HttpStatus.BAD_REQUEST);
		}
		
		//set Role to USER
	    String userRole = "USER";
	    user.setRole(roleRepo.findByRole(userRole));
		System.out.println(user.toString());

		try{
	        //Try to insert your entity by calling persist method    
	    
		// save user to repo 
			System.out.println("Saving user to DB");
			userInfoRepo.save(user);
	    }
	    catch(EntityExistsException e){
	        //Entity you are trying to insert already exist, then call merge method
	    	error = true;
	    }
	    catch(NullPointerException e) {
	    	error = true;
	    }
		if (error == false) { 
			System.out.println("User saved");
//			return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
			System.out.println(user.toString());
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		} else {
			System.out.println("User not saved");
//			return new ResponseEntity<UserInfo>(user, HttpStatus.BAD_REQUEST);
			return ResponseEntity.ok(user);
		}
	}
}
