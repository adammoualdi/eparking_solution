package com.parkingapp.server.services;

import java.util.ArrayList;
import java.util.List;

import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Service
public class ErrorService {

    public ErrorResponse createErrorResponse(BindingResult errors, String errorType) {
        List<ErrorDTO> errorList = new ArrayList<ErrorDTO>();
			System.out.println( "---------------------------------------------" + errors.getAllErrors().size());
			for (int i = 0; i < errors.getAllErrors().size(); i++) {
				// System.out.println( result.getAllErrors().get(i));
				ObjectError e = errors.getAllErrors().get(i);
				FieldError fieldError = (FieldError) e;
				String field = fieldError.getField();
				System.out.println("----------------- " + field);
				errorList.add(new ErrorDTO(field, e.getDefaultMessage()));
				System.out.println("----------------- " + errorList);
            }
            
            return new ErrorResponse(HttpStatus.OK, errorType, errorList);
	}
	
	public ErrorResponse createErrorResp(ErrorDTO errorDTO , String errorType) {
		List<ErrorDTO> errorList = new ArrayList<ErrorDTO>();
			errorList.add(errorDTO);
			// System.out.println( "---------------------------------------------" + errors.getAllErrors().size());
            return new ErrorResponse(HttpStatus.OK, errorType, errorList);
	}






}