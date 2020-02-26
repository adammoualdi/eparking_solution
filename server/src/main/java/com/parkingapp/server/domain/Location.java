package com.parkingapp.server.domain;

import java.util.List;

import javax.persistence.CascadeType;

// import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @Column(name="gps_latitude")
    private double latitude;
    @Column(name="gps_longitude")
    private double longitude;
    @Column
    private int spaces;
    @Column 
    private int costPerHour;
    @Column
    private boolean sensors;
    @Column
    private boolean approved;
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    private UserInfo userId;
    // @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL) // FOR BOOKINGS
    // @JoinColumn(name="userId")
    // private UserInfo userId;
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

    public Location(int locationId, String country, String city, String address1, String address2, String postcode, double latitude, double longitude) {
        this.locationId = locationId;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String country, String city, String address1, String address2, String postcode, double latitude,
           double longitude, int spaces) {
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spaces = spaces;
}
    
    public Location(String country, String city, String address1, String address2, String postcode, String address,
            double latitude, double longitude, List<Booking> bookings) {
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bookings = bookings;
    }

    public Location(int locationId, String country, String city, String address1, String address2, String postcode,
            String address, double latitude, double longitude, int spaces, int costPerHour, boolean sensors,
            boolean approved, UserInfo userId, List<Booking> bookings) {
        this.locationId = locationId;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spaces = spaces;
        this.costPerHour = costPerHour;
        this.sensors = sensors;
        this.approved = approved;
        this.userId = userId;
        this.bookings = bookings;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }
	

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public UserInfo getUserId() {
        return userId;
    }

    public void setUserId(UserInfo userId) {
        this.userId = userId;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    public String getAddress() {
        return address1 + " " + address2 + " " + postcode;
    }

    @Override
    public String toString() {
        return "Location [address=" + address + ", address1=" + address1 + ", address2=" + address2 + ", bookings="
                + bookings + ", city=" + city + ", country=" + country + ", latitude=" + latitude + ", locationId="
                + locationId + ", longitude=" + longitude + ", postcode=" + postcode + ", spaces=" + spaces + "]";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location(String country, String city, String address1, String address2, String postcode,
            double latitude, double longitude, int spaces, UserInfo userId) {
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spaces = spaces;
        this.userId = userId;
    }

    public int getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(int costPerHour) {
        this.costPerHour = costPerHour;
    }

    public boolean isSensors() {
        return sensors;
    }

    public void setSensors(boolean sensors) {
        this.sensors = sensors;
    }
}
