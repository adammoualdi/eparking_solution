package com.parkingapp.server.domain.DTO;

import java.time.LocalDateTime;

import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.UserInfo;

public class BookingDTO {
	private int id;
    private LocationDTO locationId;
    private UserDTO user;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private double deposit;
    private double depositFee;
    private int parkingSlotId;
    private boolean parkingConfirmation;
    private CarDTO car;
    private boolean issue;
    
    public BookingDTO() {

    }

    // public BookingDTO(LocationDTO locationId, String username, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
    //     this.locationId = locationId;
    //     this.username = username;
    //     this.startDate = startDate;
    //     this.endDate = endDate;
    //     this.active = active;
    // }

    public BookingDTO(int id, LocationDTO locationId, String username, LocalDateTime startDate, LocalDateTime endDate, boolean active, CarDTO car) {
        this.id = id;
        this.locationId = locationId;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.car = car;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocationDTO getLocationId() {
        return locationId;
    }

    public void setLocationId(LocationDTO locationId) {
        this.locationId = locationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "BookingDTO [active=" + active + ", endDate=" + endDate + ", id=" + id + ", locationId=" + locationId
                + ", startDate=" + startDate + ", userId=" + username + "]";
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public boolean isParkingConfirmation() {
        return parkingConfirmation;
    }

    public void setParkingConfirmation(boolean parkingConfirmation) {
        this.parkingConfirmation = parkingConfirmation;
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

    public UserDTO getUserDTO() {
        return user;
    }

    public void setUserDTO(UserDTO user) {
        this.user = user;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public boolean isIssue() {
        return issue;
    }

    public void setIssue(boolean issue) {
        this.issue = issue;
    }

    public double getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(double depositFee) {
        this.depositFee = depositFee;
    }

}
