package com.parkingapp.server.domain.DTO;

import java.time.LocalDate;
import java.util.List;

import com.parkingapp.server.domain.Car;

public class ProfileDTO {

	private String username;
	private String email;
	private String firstname;
	private String lastname;
    private CarsDTO cars;
    private LocalDate dofb;
	// private List<Booking> bookings;

	public ProfileDTO() {
		
    }

    public ProfileDTO(String username, String email, String firstname, String lastname, CarsDTO cars) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.cars = cars;
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

    public CarsDTO getCars() {
        return cars;
    }

    public void setCars(CarsDTO cars) {
        this.cars = cars;
    }

    public LocalDate getDofb() {
        return dofb;
    }

    public void setDofb(LocalDate dofb) {
        this.dofb = dofb;
    }
    
}
