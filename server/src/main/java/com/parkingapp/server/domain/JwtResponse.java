package com.parkingapp.server.domain;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private String username;
	private String role;
	private boolean defaultPassword;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public JwtResponse(String jwttoken, String username, String role, boolean defaultPassword) {
		this.jwttoken = jwttoken;
		this.username = username;
		this.role = role;
		this.defaultPassword = defaultPassword;
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

}