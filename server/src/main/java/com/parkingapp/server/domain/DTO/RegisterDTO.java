package com.parkingapp.server.domain.DTO;

import java.time.LocalDate;

import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.Role;

public class RegisterDTO {
	private int id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private String password2;
	private LocalDate dofb;
	private String roleName;
    private boolean defaultPassword;
    private String location;
	private String carreg;
	private int locationId;

	public RegisterDTO() {
		
    }
    
	// public RegisterDTO(int id, String usern, String pass, String email, String first, String last, String pass2, String roleName) {
	// 	this.id = id;
	// 	this.username = usern;
	// 	this.password = pass;
	// 	this.email = email;
	// 	this.firstname = first;
	// 	this.lastname = last;
	// 	this.password2 = pass2;
	// 	this.roleName = roleName;
	// }

	public RegisterDTO(int id, String usern, String pass, String email, String first, String last, String pass2, LocalDate dofb, String roleName, int locationId) {
		this.id = id;
		this.username = usern;
		this.password = pass;
		this.email = email;
		this.firstname = first;
		this.lastname = last;
		this.password2 = pass2;
		this.dofb = dofb;
		this.roleName = roleName;
		this.locationId = locationId;
	}

	// public RegisterDTO(int id, String username, String email, String firstname, String lastname, String password,
	// String password2, LocalDate dofb, Role role, String roleName, String location) {
	// 	this.id = id;
	// 	this.username = username;
	// 	this.email = email;
	// 	this.firstname = firstname;
	// 	this.lastname = lastname;
	// 	this.password = password;
	// 	this.password2 = password2;
	// 	this.dofb = dofb;
    //     this.roleName = roleName;
    //     this.location = location;
	// }

	public RegisterDTO(String usern, String pass) {
		this.username = usern;
		this.password = pass;
	}
	
	public RegisterDTO(int id, String usern, String pass) {
		this.id = id;
		this.username = usern;
		this.password = pass;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public LocalDate getDofb() {
		return dofb;
	}

	public void setDofb(LocalDate dofb) {
		this.dofb = dofb;
	}

	@Override
	public String toString() {
		return username + " " + password + " " + firstname + " " + lastname + " " + email;
	}

	public boolean isDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(boolean defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCarreg() {
        return carreg;
    }

    public void setCarreg(String carreg) {
        this.carreg = carreg;
    }

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
}
