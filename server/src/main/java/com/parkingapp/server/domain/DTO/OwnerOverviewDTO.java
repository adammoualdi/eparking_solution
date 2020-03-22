package com.parkingapp.server.domain.DTO;

public class OwnerOverviewDTO {
    
    private UserDTO user;
    private BookingDTO booking;

    public OwnerOverviewDTO() {

    }

    public OwnerOverviewDTO(UserDTO user, BookingDTO booking) {
        this.user = user;
        this.booking = booking;
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

}
