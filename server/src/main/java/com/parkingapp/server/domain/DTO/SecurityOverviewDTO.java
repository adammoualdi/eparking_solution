package com.parkingapp.server.domain.DTO;

import java.util.List;

import com.parkingapp.server.domain.Booking;

public class SecurityOverviewDTO {
    
    List<BookingDTO> bookings;

    public SecurityOverviewDTO(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }

    

    
}
