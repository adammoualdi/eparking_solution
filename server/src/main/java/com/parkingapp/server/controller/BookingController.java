package com.parkingapp.server.controller;

import java.nio.charset.Charset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.CarDTO;
import com.parkingapp.server.domain.DTO.CarsDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.LocationsDistanceDTO;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.CarRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.EmailServicer;
import com.parkingapp.server.services.ErrorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
public class BookingController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;
    @Autowired CarRepository carRepo;

    @Autowired EmailServicer emailServicer;
    @Autowired ErrorService errorService;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
    public ResponseEntity<?> saveBooking(@RequestHeader("Authorization") String token, 
                                         HttpServletRequest request,
                                         @RequestBody BookingDTO bookingDetails) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);

        Location loc = locationRepo.findByLocationId(bookingDetails.getLocationId().getLocationId());                                            
        Booking booking = new Booking();

        ArrayList<Booking> bookings = bookingRepo.findBookings(loc, bookingDetails.getStartDate(), bookingDetails.getEndDate());
        // Sort bookings by their sensor id for allocation
        Collections.sort(bookings);

        // Booking bestfitBooking = new Booking();
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

        if ( booking.getParkingSlotId() == 0 ) {
            return ResponseEntity.ok("Error");
        }

        long hours = ChronoUnit.HOURS.between(bookingDetails.getStartDate(), bookingDetails.getEndDate());
        // As we times by 100 we shouldn't get a long type value therefore can be converted to an int
        double costForBooking = (hours * loc.getCostPerHour());
        // Check if user has correct amount of points for purchase
        if (costForBooking > user.getDeposit()) {
            return ResponseEntity.ok("Doesn't have enough points");
        } else {
            // Remove points from user
            user.setDeposit(user.getDeposit() - costForBooking);
            System.out.println(user.getDeposit());
            userRepo.save(user); 
            bookingDetails.setDeposit(user.getDeposit());
        }

        // Generate url for booking confirmation
        String url;
        while(true) {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        	StringBuilder urlBuilder = new StringBuilder();
        	Random rnd = new Random();
        	while (urlBuilder.length() < 32) { // length of the random string.
            	int index = (int) (rnd.nextFloat() * SALTCHARS.length());
				urlBuilder.append(SALTCHARS.charAt(index));
            }
            url = urlBuilder.toString();
            Booking checkUrlExists = bookingRepo.findByBookingUrl(url);
            if (checkUrlExists ==  null) {
                break;
            }
        }
        
        booking.setBookingUrl(url);
		booking.setLocationId(loc);
		booking.setUserId(user);
        booking.setStartDate(bookingDetails.getStartDate());
        booking.setEndDate(bookingDetails.getEndDate());
        booking.setActive(true);
        booking.setFee(costForBooking);
        System.out.println(bookingDetails.getCar().getId());
        Car car = carRepo.findById(bookingDetails.getCar().getId());

        
        
        // Car car = new Car(user, bookingDetails.getCar().getRegNo(), bookingDetails.getCar().getModel());
        booking.setCar(car);

        
        try {
            bookingRepo.save(booking);
            // System.out.println(request.getRequestURL()); 
            String serverUrl = "https://localhost:8080/booking/confirmation/";  
            emailServicer.parkingConfirmationEmail(booking.getUserId().getUsername(), booking.getUserId().getEmail(), booking.getBookingUrl(), serverUrl);
        } catch (Exception e) {
            // return ResponseEntity.ok()
        }

	    return ResponseEntity.ok(bookingDetails);
    }

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/booking/confirmation/{url}", method = RequestMethod.POST)
    public ResponseEntity<?> saveBooking(@RequestHeader("Authorization") String token,
                                         @PathVariable("url") String url) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        
        Booking booking = bookingRepo.findByBookingUrl(url);
        if (booking == null) {
            ErrorDTO error = new ErrorDTO("url", "URL not found");
            ErrorResponse er = errorService.createErrorResp(error, "Not found");
            return ResponseEntity.ok(er);
        }

        if (!booking.getUserId().equals(user)) {
            ErrorDTO error = new ErrorDTO("url", "Incorrect user");
            ErrorResponse er = errorService.createErrorResp(error, "Incorrect user");
            return ResponseEntity.ok(er);
        }

        if (booking.isParkingConfirmation() == true) {
            ErrorDTO error = new ErrorDTO("url", "Already been confirmed");
            ErrorResponse er = errorService.createErrorResp(error, "Already active");
            return ResponseEntity.ok(er);
        }

        booking.setParkingConfirmation(true);
        return ResponseEntity.ok("success"); 
    }

    
}