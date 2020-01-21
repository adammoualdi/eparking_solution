package com.parkingapp.server.domain.DTO;

import com.parkingapp.server.domain.UserInfo;

public class AdminLocationDTO {

    private int id;
    private String country;
    private String city;
    private String address1;
    private String address2;
    private String postcode;
    private double latitude;
    private double longitude;
    private int spaces;
    private UserDTO userId;
    private boolean warning;
    private boolean approved;

	public AdminLocationDTO() {
		
    }

    public AdminLocationDTO(int id, String country, String city, String address1, String address2, String postcode,
            double latitude, double longitude) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AdminLocationDTO(int id, String country, String city, String address1, String address2, String postcode,
    double latitude, double longitude, int spaces, UserDTO userId, boolean warning, boolean approved) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.spaces = spaces;
        this.userId = userId;
        this.warning = warning;
        this.approved = approved;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DashboardLocationDTO [address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", country="
                + country + ", latitude=" + latitude + ", longitude=" + longitude + ", postcode=" + postcode + "]";
    }

    public UserDTO getUserId() {
        return userId;
    }

    public void setUserId(UserDTO userId) {
        this.userId = userId;
    }

    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
