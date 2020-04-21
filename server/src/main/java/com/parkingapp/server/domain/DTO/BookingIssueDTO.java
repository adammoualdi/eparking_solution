package com.parkingapp.server.domain.DTO;

import java.time.LocalDateTime;

import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.UserInfo;

public class BookingIssueDTO {
    private int id;
    private int locationId;
    // private LocationDTO locationId;
    private BookingDTO booking;
    private String regno;

    public BookingIssueDTO() {
        
    }

    // public BookingIssueDTO(LocationDTO locationId, BookingDTO booking) {
    //     this.locationId = locationId;
    //     this.booking = booking;
    // }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
