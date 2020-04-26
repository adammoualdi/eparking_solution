package com.parkingapp.server.util;
import java.util.Comparator;

import com.parkingapp.server.domain.Booking;

public class SortbyParkingLength implements Comparator<Booking> {
    // Used for sorting in ascending order of 
    // ID number 
    public int compare(Booking a, Booking b) { 
        int n;
        n = a.getLength() - b.getLength();
        if (n == 0) {
            n = a.getBookingsPerParkingSlot() - a.getBookingsPerParkingSlot();        }
        return n;
        // return (int)a.getLength() - (int)b.getLength(); 
    } 
} 