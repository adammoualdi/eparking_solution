package com.parkingapp.server.domain;

import java.util.List;

// import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
// import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Saves instances of Roles in the Role entity
 * */
@Entity
@Table(name="locations")
public class Location {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="location_id")
	private int locationId;
	@Column
    private String country;
    @Column
    private String city;
    @Column
    private String address1;
    @Column 
    private String address2;
    @Column 
    private String postcode;
    @Transient 
    private String address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "locationId")
	private List<Booking> bookings;

    public Location() {

    }

    public Location(String country, String city, String address1, String address2, String postcode) {
        // this.locationId = locationId;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
    }

    public Location(int locationId, String country, String city, String address1, String address2, String postcode) {
        this.locationId = locationId;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
    public String getAddress() {
        return address1 + " " + address2 + " " + postcode;
    }

    @Override
    public String toString() {
        return "Location [address=" + address + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city
                + ", country=" + country + ", locationId=" + locationId + ", postcode=" + postcode + "]";
    }
	
}
