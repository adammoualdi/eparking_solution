package com.parkingapp.server.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity(name="users")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int id;
//	@NotNull
//	@Size(min=3, message="Name should have atleast 3 characters")
	@Column
	private String username;
	@Column 
	private String email;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Column
	private String password;
	@Transient
	private String password2;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="role", referencedColumnName="id")
	private Role role;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
	// @JoinColumn(name="cars", referencedColumnName="id")
	private List<Car> cars;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
	private List<Booking> bookings;
	
    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    // private List<Email> emails;	
	// @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	// @JoinColumn(name="location_test", referencedColumnName="location_id")
	// private LocationTest location;

	public UserInfo() {
		
	}

	public UserInfo(int id, String usern, String pass, String email, String first, String last, String pass2) {
		this.id = id;
		this.username = usern;
		this.password = pass;
		this.email = email;
		this.firstname = first;
		this.lastname = last;
		this.password2 = pass2;
	}
	
	public UserInfo(int id, String usern, String pass, String email, String first, String last, String pass2, List<Car> cars) {
		this.id = id;
		this.username = usern;
		this.password = pass;
		this.email = email;
		this.firstname = first;
		this.lastname = last;
		this.password2 = pass2;
		this.cars = cars;
	}

	public UserInfo(int id, String usern, String pass, String email, String first, String last, String pass2, List<Car> cars, List<Booking> bookings) {
		this.id = id;
		this.username = usern;
		this.password = pass;
		this.email = email;
		this.firstname = first;
		this.lastname = last;
		this.password2 = pass2;
		this.cars = cars;
		this.bookings = bookings;
	}

	public UserInfo(String usern, String pass) {
		this.username = usern;
		this.password = pass;
	}
	
	public UserInfo(int id, String usern, String pass) {
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
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return username + " " + password + " " + firstname + " " + lastname + " " + email + " " + role;
	}

	// public LocationTest getLocation() {
	// 	return location;
	// }

	// public void setLocation(LocationTest location) {
	// 	this.location = location;
	// }
	
}
