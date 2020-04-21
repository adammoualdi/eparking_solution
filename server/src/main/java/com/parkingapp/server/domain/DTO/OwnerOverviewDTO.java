package com.parkingapp.server.domain.DTO;

public class OwnerOverviewDTO {
    
    private UserDTO user;
    private BookingDTO booking;
    private LocationDTO location;

    public OwnerOverviewDTO() {

    }

    public OwnerOverviewDTO(UserDTO user, BookingDTO booking, LocationDTO location) {
        this.user = user;
        this.booking = booking;
        this.location = location;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

}
