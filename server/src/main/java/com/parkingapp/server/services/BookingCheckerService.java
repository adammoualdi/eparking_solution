package com.parkingapp.server.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.parkingapp.server.repository.CarRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;

import com.parkingapp.server.EParkingApplication;
import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.util.SortbyParkingSlot;
import com.parkingapp.server.util.SortbyParkingLength;

@Service
public class BookingCheckerService extends EmailServicer {

    @Autowired
    LocationRepo locationRepo;
    @Autowired
    BookingRepository bookingRepo;
    @Autowired
    UserInfoRepo userRepo;
    @Autowired
    CarRepository carRepo;

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

    public Booking getParkingSlot(Location loc, LocalDateTime arrivingTime, LocalDateTime leavingTime, int bookingId) {
        Booking booking = new Booking();
        System.out.println("Arrive Time: " + arrivingTime);
        System.out.println("Leaving Time: " + leavingTime);
        System.out.println("Param ID: " + bookingId);
        ArrayList<Booking> bookings = bookingRepo.findBookings(loc, arrivingTime, leavingTime, bookingId);
        // Sort bookings by their sensor id for allocation
        Collections.sort(bookings);

        // Booking bestfitBooking = new Booking();
        System.out.println("SIZE " + bookings.size());
        for (int ii = 0; ii < bookings.size(); ii++) {
            System.out.println("BOOKING ID: " + bookings.get(ii).getId());
        }
        if (bookings.size() == 0) {
            booking.setParkingSlotId(1);
        } else if (bookings.size() == 1) {
            booking.setParkingSlotId(2);
        } else {
            boolean found = false;
            // check for gaps in parking slot ids
            for (int i = 1; i < bookings.size(); i++) {
                // if (bookings.get(i).)
                if (i == 1 && bookings.get(i - 1).getParkingSlotId() != 1) {
                    System.out.println("PARKING SLOT AT START");
                    booking.setParkingSlotId(1);
                    found = true;
                    break;
                }
                System.out.println(bookings.get(i).getParkingSlotId() + " - " + bookings.get(i - 1).getParkingSlotId());
                if (bookings.get(i).getParkingSlotId() - bookings.get(i - 1).getParkingSlotId() > 1) {
                    System.out.println(bookings.get(i - 1).getParkingSlotId());
                    booking.setParkingSlotId(bookings.get(i).getParkingSlotId());
                    found = true;
                    break;
                }
            }
            // FIRST FIT
            // if no gaps in parking slot ids and the last sensor id is smaller than the
            // amount of spaces
            if ((found == false) && (bookings.get(bookings.size() - 1).getParkingSlotId() < loc.getSpaces())) {
                booking.setParkingSlotId(bookings.get(bookings.size() - 1).getParkingSlotId() + 1);
            }
        }

        System.out.println("FIRST FIT PARKING ID " + booking.getParkingSlotId());

        // if ( booking.getParkingSlotId() == 0 ) {
        // return ResponseEntity.ok("Error");
        // }
        return booking;
    }

    // public Booking getParkingSlot(Location loc, Booking booking) {
    // // Booking booking = new Booking();
    // ArrayList<Booking> bookings = bookingRepo.findBookings(loc,
    // booking.getStartDate(), booking.getEndDate(), booking.getId());
    // // Sort bookings by their sensor id for allocation
    // Collections.sort(bookings);

    // // Booking bestfitBooking = new Booking();
    // System.out.println("SIZE " + bookings.size());
    // if (bookings.size() == 0) {
    // booking.setParkingSlotId(1);
    // } else if (bookings.size() == 1) {
    // booking.setParkingSlotId(2);
    // } else {
    // boolean found = false;
    // // check for gaps in parking slot ids
    // for (int i = 1; i < bookings.size(); i++) {
    // // if (bookings.get(i).)
    // System.out.println(bookings.get(i).getParkingSlotId() + " - " +
    // bookings.get(i-1).getParkingSlotId());
    // if (bookings.get(i).getParkingSlotId() - bookings.get(i -
    // 1).getParkingSlotId() > 1) {
    // System.out.println(bookings.get(i-1).getParkingSlotId());
    // booking.setParkingSlotId(bookings.get(i).getParkingSlotId());
    // found = true;
    // break;
    // }
    // }
    // // FIRST FIT
    // // if no gaps in parking slot ids and the last sensor id is smaller than the
    // amount of spaces
    // if ( (found == false) && (bookings.get(bookings.size()-1).getParkingSlotId()
    // < loc.getSpaces()) ) {
    // booking.setParkingSlotId(bookings.get(bookings.size()-1).getParkingSlotId() +
    // 1);
    // }
    // }

    // System.out.println("FIRST FIT PARKING ID " + booking.getParkingSlotId());

    // // if ( booking.getParkingSlotId() == 0 ) {
    // // return ResponseEntity.ok("Error");
    // // }
    // return booking;
    // }

    public Booking optimiseParkingSlots(Location loc, LocalDateTime arrivingTime, LocalDateTime leavingTime,
            int bookingId) {
        Booking booking = new Booking();
        ArrayList<Booking> bookings = bookingRepo.findBookings(loc, arrivingTime, leavingTime, bookingId);
        // Sort by booking ID.
        Collections.sort(bookings);

        // Get lengths of all bookings
        System.out.println("SIZE " + bookings.size());
        for (int i = 0; i < bookings.size(); i++) {
            Duration prevDuration = Duration.between(bookings.get(i).getStartDate(), bookings.get(i).getEndDate());
            long prevBookingSeconds = Math.abs(prevDuration.getSeconds());
            long prevBookingMinutes = prevBookingSeconds / 60;
            bookings.get(i).setLength((int) prevBookingMinutes);
            System.out.println(bookings.get(i).getLength());
            System.out.println("ID " + bookings.get(i).getId());
        }

        Map<Integer, List<Booking>> mapBookings = new HashMap<>();
        // Get number of bookings per parking slot for optimisation sort.
        for (int j = 1; j < bookings.size(); j++) {
            System.out.println(bookings.get(j - 1).getParkingSlotId() + "==" + bookings.get(j).getParkingSlotId());
            if (bookings.get(j - 1).getParkingSlotId() == bookings.get(j).getParkingSlotId()) {
                bookings.get(j).setBookingsPerParkingSlot(bookings.get(j).getBookingsPerParkingSlot() + 1);
                bookings.get(j - 1).setBookingsPerParkingSlot(bookings.get(j).getBookingsPerParkingSlot() + 1);

                if (j == 1) {
                    System.out.println("PUT " + bookings.get(j - 1).getId() + " IN MAP");
                    List<Booking> newList = new ArrayList<Booking>();
                    newList.add(bookings.get(j - 1));
                    mapBookings.put(bookings.get(j - 1).getParkingSlotId(), newList);
                }

                // If not in map
                System.out.println("TEST " + bookings.get(j).getBookingsPerParkingSlot());
                if (bookings.get(j - 1).getBookingsPerParkingSlot() == 1) {
                    // System.out.println(bookings.get(j));
                    List<Booking> list = new ArrayList<Booking>();
                    list.add(bookings.get(j));
                    System.out.println("PUT " + bookings.get(j).getId() + " IN MAP");
                    mapBookings.put(bookings.get(j).getParkingSlotId(), list);
                } else {
                    System.out.println("ADD " + bookings.get(j).getId() + " IN MAP");
                    mapBookings.get(bookings.get(j).getParkingSlotId()).add(bookings.get(j));
                }
            } else {
                bookings.get(j - 1).setBookingsPerParkingSlot(bookings.get(j - 1).getBookingsPerParkingSlot() + 1);
                bookings.get(j).setBookingsPerParkingSlot(1);
                if (j == 1) {
                    List<Booking> newList = new ArrayList<Booking>();
                    System.out.println("PUT " + bookings.get(j - 1).getId() + " IN MAP");
                    newList.add(bookings.get(j - 1));
                    mapBookings.put(bookings.get(j - 1).getParkingSlotId(), newList);
                }
                if (bookings.get(j).getBookingsPerParkingSlot() == 1) {
                    List<Booking> newList = new ArrayList<Booking>();
                    System.out.println("PUT " + bookings.get(j).getId() + " IN MAP");
                    newList.add(bookings.get(j));
                    mapBookings.put(bookings.get(j).getParkingSlotId(), newList);
                } else {
                    System.out.println("ADD " + bookings.get(j).getId() + " IN MAP");
                    mapBookings.get(bookings.get(j).getParkingSlotId()).add(bookings.get(j));
                }
            }
            System.out.println("NUMBER OF PARKING SLOTS " + bookings.get(j).getBookingsPerParkingSlot() + " BOOKING ID "
                    + bookings.get(j).getId());
        }

        // NEED CHECK THERE'S NOT TWO BOOKINGS ON SAME PARKING SLOT

        // Sort bookings by their parking length and number of bookings per slot where
        // smallest is first
        Collections.sort(bookings, new SortbyParkingLength());

        List<Integer> parkingSlots = new ArrayList<Integer>();
        for (int iii = 0; iii < bookings.size(); iii++) {
            // Do this so we know what are the best parking slots to check first
            if (!parkingSlots.contains(bookings.get(iii).getParkingSlotId())) {
                parkingSlots.add(bookings.get(iii).getParkingSlotId());
            }
        }

        // Loop through parking slots.
        System.out.println("SIZE OF PARKINGSLOTIDS ARRAY " + parkingSlots.size());
        for (int ii = 0; ii < parkingSlots.size(); ii++) {
            boolean breaked = false;
            System.out.println("PARKING SLOT " + parkingSlots.get(ii));

            List<Booking> tmpBookings = mapBookings.get(parkingSlots.get(ii));
            List<Booking> changeBookings = new ArrayList<Booking>();

            // For all bookings in one parking slot
            // Can be done seperately due to the fact that they will all have different time
            // slots thus
            // can't change eachother.
            for (int iii = 0; iii < tmpBookings.size(); iii++) {
                System.out.println("BOOKING ID " + tmpBookings.get(iii).getId());
                Booking tmpBooking = getParkingSlot(loc, tmpBookings.get(iii).getStartDate(),
                        tmpBookings.get(iii).getEndDate(), 0);
                tmpBooking.setId(tmpBookings.get(iii).getId());
                if ((tmpBooking.getParkingSlotId() == 0)) {
                    System.out.println("NOT FOUND NEW SLOT FOR ID " + parkingSlots.get(ii));
                    breaked = true;
                    break;
                } else {
                    System.out.println("FOUND NEW SLOTS FOR PARKING SLOT ID " + parkingSlots.get(ii));
                    changeBookings.add(tmpBooking);
                }
            }
            if (breaked == false) {
                // Set new booking with new parking slot ID
                booking.setParkingSlotId(parkingSlots.get(ii));
                // Change all parking slots with new parking IDs
                // bookingRepo.saveAll(changeBookings);
                for (int i = 0; i < changeBookings.size(); i++) {
                    Booking bookingSave = bookingRepo.findById(changeBookings.get(i).getId());
                    bookingSave.setParkingSlotId(changeBookings.get(i).getParkingSlotId());
                    bookingRepo.save(bookingSave);
                }
                break;
            }
        }

        return booking;

    }

    public double calculateCost(LocalDateTime startDate, LocalDateTime endDate, Location loc) {
        long hours = ChronoUnit.HOURS.between(startDate, endDate);
        double costForBooking = (hours * loc.getCostPerHour());
        return costForBooking;
    }

    @Scheduled(fixedRate = 500000) // , cron = "0 0 0 * * ?")
    public void checkCurrentUserBookings() {
        System.out.println("CHECKING FINISHED USER BOOKINGS");
        LocalDateTime currentTime = LocalDateTime.now().minusMinutes(30);
        System.out.println(currentTime);
        // get list of current bookings.
        List<Booking> bookings = bookingRepo.findNotCompletedBookings(currentTime);

        for (int i = 0; i < bookings.size(); i++) {
            System.out.println(bookings.get(i).getId());
            UserInfo user = userRepo.findById(bookings.get(i).getUserId().getId());
            if (bookings.get(i).isIssue() == false) {
                user.setDeposit(user.getDeposit() + bookings.get(i).getDepositFee());
                user.setRequiredDeposit(user.getRequiredDeposit() - bookings.get(i).getDepositFee());
                userRepo.save(user);
            } else {
                Car car = carRepo.findById(bookings.get(i).getCar().getCarId());
                try {
                    reportIssue(user.getEmail(), bookings.get(i).getDepositFee(), car.getRegNo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bookings.get(i).setCompleted(true);
            bookingRepo.save(bookings.get(i));
        }
    }
	
}