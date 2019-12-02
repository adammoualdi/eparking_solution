package com.parkingapp.server.domain.DTO;

public class LocationDTO {
	private int locationId;
    private String country;
    private String city;
    private String address1;
    private String address2;
    private String postcode;
    private double latitude;
    private double longitude;
    private double distance;
    private String arriveTime;
    private String leavingTime;
    
    public LocationDTO() {

    }

    public LocationDTO(int locationId, String country, String city, String address1, String address2, String postcode) {
        this.locationId = locationId;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
    }

    public LocationDTO(int locationId, String country, String city, String address1, String address2, String postcode,
                        double distance, double longitude, double latitude, String arriveTime, String leavingTime) 
    {
        this.locationId = locationId;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.arriveTime = arriveTime;
        this.leavingTime = leavingTime;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    @Override
    public String toString() {
        return "LocationDTO [address1=" + address1 + ", address2=" + address2 + ", arriveTime=" + arriveTime + ", city="
                + city + ", country=" + country + ", distance=" + distance + ", latitude=" + latitude + ", leavingTime="
                + leavingTime + ", locationId=" + locationId + ", longitude=" + longitude + ", postcode=" + postcode
                + "]";
    }
	
}
