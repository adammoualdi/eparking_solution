package com.parkingapp.server.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

import com.parkingapp.server.domain.UserInfo;
	
	public class RegisterValidator implements Validator {
		
		public boolean supports(Class<?> clazz) {
	        return UserInfo.class.equals(clazz);
	    }
		
		@Override
		public void validate(Object target, Errors errors) {
			UserInfo dto = (UserInfo) target;

			// Checks for blank spaces and if inputs are within specification
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "", "Username field cannot be empty.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Password field cannot be empty.");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password2", "", "Password field cannot be empty.");
			if(dto.getUsername().length() <= 4) {
				errors.rejectValue("username", "", "Username must be longer than 4 characters");
			}
			if(dto.getUsername().length() >= 20) {
				errors.rejectValue("username", "", "Username must be shorter than 12 characters");
			}
			if (dto.getPassword().length() <= 6) {
				errors.rejectValue("password", "", "Password must be larger than 5 characters");
			}
			if (dto.getPassword().length() >= 20) {
				errors.rejectValue("password", "", "Password must be smaller than 20 characters");
			}
			if(!dto.getPassword().equals(dto.getPassword2())) {
				errors.rejectValue("password2", "", "The confirmation password is different");
			}
			if(dto.getFirstname().length() <= 2 ) {
				errors.rejectValue("firstname", "" , "First name must be larger than 2 characters");
			} 
			if (dto.getFirstname().length() >= 20) {
				errors.rejectValue("firstname", "", "First name must be smaller than 20 characters");
			}
			if(dto.getLastname().length() <= 2 ) {
				errors.rejectValue("lastname", "" , "Last name must be larger than 2 characters");
			}
			if (dto.getLastname().length() >= 20) {
				errors.rejectValue("lastname", "", "Last name must be smaller than 20 characters");
			} 
//	
			//  For testing when we want to send alerts to our emails.
			//	"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
			Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
		            Pattern.CASE_INSENSITIVE);
			// Pattern pattern2 = Pattern.compile("^[A-Za-z0-9._%+-]+@gmail.com$",
		    //         Pattern.CASE_INSENSITIVE);
		    if (!(pattern.matcher(dto.getEmail()).matches())) {
		         errors.rejectValue("email", "", "Email entered is invalid");
			}
		}
}