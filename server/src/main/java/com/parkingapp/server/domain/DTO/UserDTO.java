package com.parkingapp.server.domain.DTO;

import java.time.LocalDate;

public class UserDTO {
	private int id;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private LocalDate dofb;
    
	public UserDTO() {
		
	}

    public UserDTO(int id, String username, String email, String firstname, String lastname, String password,
            LocalDate dofb) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.dofb = dofb;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDofb() {
        return dofb;
    }

    public void setDofb(LocalDate dofb) {
        this.dofb = dofb;
    }
	
}
