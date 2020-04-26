package com.parkingapp.server.util;
import java.util.Comparator;

import com.parkingapp.server.domain.Booking;

public class SortbyParkingSlot implements Comparator<Booking> {
    // Used for sorting in ascending order of 
    // ID number 
    public int compare(Booking a, Booking b) { 
        return a.getParkingSlotId() - b.getParkingSlotId(); 
    } 
} 