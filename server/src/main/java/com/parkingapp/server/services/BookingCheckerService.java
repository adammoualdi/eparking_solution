package com.parkingapp.server.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;

import com.parkingapp.server.EParkingApplication;
import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;

@Service
public class BookingCheckerService {
	 
    @Autowired LocationRepo locationRepo;
    @Autowired BookingRepository bookingRepo;
    
    public boolean checkAvailability(LocalDateTime arrivingTime, LocalDateTime leavingTime, LocationDTO locationDto) {
        return true;
    }

    // Returns true if booking already exists with that car at the same time.
    public boolean carBookingExists(BookingDTO booking, UserInfo user) {
        List<Booking> bookings = bookingRepo.findUserBookings(user, booking.getStartDate(), booking.getEndDate());
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println(bookings.get(i).getId());
            if (bookings.get(i).getCar().getRegNo().equals(booking.getCar().getRegNo())) {
                return true;
            }
        }
        return false;
    }

    public Booking getParkingSlot(Location loc, LocalDateTime arrivingTime, LocalDateTime leavingTime) {
        Booking booking = new Booking();
        ArrayList<Booking> bookings = bookingRepo.findBookings(loc, arrivingTime, leavingTime);
        // Sort bookings by their sensor id for allocation
        Collections.sort(bookings);

        // Booking bestfitBooking = new Booking();
        System.out.println(bookings.size());
        if (bookings.size() == 0) {
            booking.setParkingSlotId(1);
        } else if (bookings.size() == 1) {
            booking.setParkingSlotId(2);
        } else {
            boolean found = false;
            // check for gaps in parking slot ids
            for (int i = 1; i < bookings.size(); i++) {
                System.out.println(bookings.get(i).getParkingSlotId() + " - " + bookings.get(i-1).getParkingSlotId());
                if (bookings.get(i).getParkingSlotId() - bookings.get(i - 1).getParkingSlotId() > 1) {
                    System.out.println(bookings.get(i-1).getParkingSlotId());
                    booking.setParkingSlotId(bookings.get(i).getParkingSlotId());
                    found = true;
                    break;
                }
            }
            // FIRST FIT ALGORITHM
            // if no gaps in parking slot ids and the last sensor id is smaller than the amount of spaces
            if ( (found == false) && (bookings.get(bookings.size()-1).getParkingSlotId() < loc.getSpaces()) ) {
                booking.setParkingSlotId(bookings.get(bookings.size()-1).getParkingSlotId() + 1);
            }
        }

        // if ( booking.getParkingSlotId() == 0 ) {
        //     return ResponseEntity.ok("Error");
        // }
        return booking;
    }

    public Booking optimiseParkingSlots(Location loc, LocalDateTime arrivingTime, LocalDateTime leavingTime) {
        Booking booking = new Booking();
        ArrayList<Booking> bookings = bookingRepo.findBookings(loc, arrivingTime, leavingTime);
        // Sort by booking ID.
        Collections.sort(bookings);

        // SORT BY LENGTH OF BOOKINGS SMALLEST LENGTHS FIRST
        // MOVE BOOKING TO NEXT AVAILABLE SLOT IF POSSIBLE
            // IF POSSIBLE MOVE BOOKING TO THAT SLOT
            // ELSE TRY NEXT BOOKING
                // ADD PARKING SLOT ID TO DATA STRUCTURE

        // Get lengths of all bookings
        for (int i = 0; i < bookings.size(); i++) {
            Duration prevDuration = Duration.between(bookings.get(i).getStartDate(), bookings.get(i).getEndDate());
            long prevBookingSeconds = Math.abs(prevDuration.getSeconds());
            long prevBookingMinutes = prevBookingSeconds/60;
            bookings.get(i).setLength(prevBookingMinutes);
            System.out.println(bookings.get(i).getLength());
            // if (prevBookingMinutes == 30) {
                
            // }
            // if ()
        }
        // SortbyId spl = new SortbyParkingLength()
        // Collections.sort(bookings, );


        return booking;

    }

    public double calculateCost(LocalDateTime startDate, LocalDateTime endDate, Location loc) {
        long hours = ChronoUnit.HOURS.between(startDate, endDate);
        double costForBooking = (hours * loc.getCostPerHour());
        return costForBooking;
    }


    @Scheduled(fixedRate = 30000, cron = "0 0 0 * * ?")
    public void checkCurrentUserBookings() {

    }
	
}