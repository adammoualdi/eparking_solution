package com.parkingapp.server.domain;

import java.io.Serializable;

import com.parkingapp.server.domain.DTO.CarsDTO;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private String username;
	private String role;
	private boolean defaultPassword;
	private double deposit;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public JwtResponse(String jwttoken, String username, String role, boolean defaultPassword, double deposit) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.role = role;
		this.defaultPassword = defaultPassword;
		this.deposit = deposit;
	}

	public String getToken() {
		return this.jwttoken;

	}
	
	public String getUsername() {
		return username;
	}

	public String getRole() {
		return this.role;
	}

	public boolean isDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(boolean defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

}