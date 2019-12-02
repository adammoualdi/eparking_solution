package com.parkingapp.server.domain.DTO;

import java.util.List;

import com.parkingapp.server.domain.Booking;

public class UserBookingsDTO {
    
    List<UserBookingDTO> bookings;

    public UserBookingsDTO() {
        
    }

    public UserBookingsDTO(List<UserBookingDTO> bookings) {
        this.bookings = bookings;
    }

    public List<UserBookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<UserBookingDTO> bookings) {
        this.bookings = bookings;
    }
    
}
