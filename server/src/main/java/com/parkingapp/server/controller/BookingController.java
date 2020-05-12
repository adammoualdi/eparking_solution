package com.parkingapp.server.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.BookingIssueDTO;
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
import com.parkingapp.server.services.BookingCheckerService;
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
    @Autowired BookingCheckerService bookingService;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
    public ResponseEntity<?> saveBooking(@RequestHeader("Authorization") String token, 
                                         HttpServletRequest request,
                                         @RequestBody BookingDTO bookingDetails) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);

        Location loc = locationRepo.findByLocationId(bookingDetails.getLocationId().getLocationId());                                            
        // Booking booking = new Booking();

        // Checks to see if the user has an existing booking with the same car overlapping with this booking.
        boolean existingBooking = bookingService.carBookingExists(bookingDetails, user);
        if (existingBooking == true) {
            ErrorDTO error = new ErrorDTO("Exists", "Booking clash with this car");
            ErrorResponse er = errorService.createErrorResp(error, "Exists");
            return ResponseEntity.ok(er);
            // return ResponseEntity.ok("Existing booking already exists");
        }

        // ArrayList<Booking> bookings = bookingRepo.findBookings(loc, bookingDetails.getStartDate(), bookingDetails.getEndDate());
        // Sort bookings by their sensor id for allocation
        // Collections.sort(bookings);

        // Booking bestfitBooking = new Booking();
        // if (bookings.size() == 0) {
        //     booking.setParkingSlotId(1);
        // } else if (bookings.size() == 1) {
        //     booking.setParkingSlotId(2);
        // } else {
        //     boolean found = false;
        //     // check for gaps in parking slot ids
        //     for (int i = 1; i < bookings.size(); i++) {
        //         System.out.println(bookings.get(i).getParkingSlotId() + " - " + bookings.get(i-1).getParkingSlotId());
        //         if (bookings.get(i).getParkingSlotId() - bookings.get(i - 1).getParkingSlotId() > 1) {
        //             System.out.println(bookings.get(i-1).getParkingSlotId());
        //             booking.setParkingSlotId(bookings.get(i).getParkingSlotId());
        //             found = true;
        //             break;
        //         }
        //     }
        //     // FIRST FIT ALGORITHM
        //     // if no gaps in parking slot ids and the last sensor id is smaller than the amount of spaces
        //     if ( (found == false) && (bookings.get(bookings.size()-1).getParkingSlotId() < loc.getSpaces()) ) {
        //         booking.setParkingSlotId(bookings.get(bookings.size()-1).getParkingSlotId() + 1);
        //     }
        // }
        // Booking booking = new Booking();
        // booking.setStartDate(bookingDetails.getStartDate());
        // booking.setEndDate(bookingDetails.getEndDate());
        Booking booking = bookingService.getParkingSlot(loc, bookingDetails.getStartDate(), bookingDetails.getEndDate(), 0);
        // booking = bookingService.getParkingSlot(loc, booking);

        if ( booking.getParkingSlotId() == 0 ) {
            booking = bookingService.optimiseParkingSlots(loc, bookingDetails.getStartDate(), bookingDetails.getEndDate(), 0);
            if ( booking.getParkingSlotId() == 0 ) {
                return ResponseEntity.ok("No parking space available");
            }
        }

        // long hours = ChronoUnit.HOURS.between(bookingDetails.getStartDate(), bookingDetails.getEndDate());
        // double costForBooking = (hours * loc.getCostPerHour());
        // // Check if user has correct amount of points for purchase
        // if (costForBooking > user.getDeposit()) {
        //     return ResponseEntity.ok("Doesn't have enough points");
        // } else {
        //     // Remove points from user
        //     user.setDeposit(user.getDeposit() - costForBooking);
        //     System.out.println(user.getDeposit());
        //     userRepo.save(user); 
        //     bookingDetails.setDeposit(user.getDeposit());
        // }
        // UserInfo checkUser = user;
        double fee = bookingService.calculateCost(bookingDetails.getStartDate(), bookingDetails.getEndDate(), loc);
        double depositFee = fee * 0.8;
        System.out.println("DEPOSIT FEE: " + depositFee);
        System.out.println("FEE: " + fee);
        if (fee + depositFee > user.getDeposit()) {
                return ResponseEntity.ok("Doesn't have enough points");
        } else {
            // Add money to requiredDeposit and remove from deposit
            double totalFee = fee + depositFee;
            System.out.println("FEE: " + totalFee);
            user.setRequiredDeposit(user.getRequiredDeposit() + depositFee);
            user.setDeposit(user.getDeposit() - totalFee);
            // System.out.println("Fee:" + fee);
            userRepo.save(user);
            System.out.println("DEPOSIT: "  + user.getDeposit()); 
            bookingDetails.setDepositFee(depositFee);
            BigDecimal bd = new BigDecimal(user.getDeposit()).setScale(3, RoundingMode.HALF_UP);
            System.out.println("NEW DEPOSIT: "  + bd.doubleValue()); 
            bookingDetails.setDeposit(bd.doubleValue());
        }
        BigDecimal bdd = new BigDecimal(user.getDeposit()).setScale(3, RoundingMode.HALF_UP);
        bookingDetails.setDeposit(bdd.doubleValue());

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
        booking.setFee(fee);
        BigDecimal bd = new BigDecimal(depositFee).setScale(2, RoundingMode.HALF_UP);
        System.out.println("ROUND UP: " + bd.doubleValue());
        booking.setDepositFee(bd.doubleValue());
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
        bookingRepo.save(booking);
        return ResponseEntity.ok("success"); 
    }

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/booking/issue", method = RequestMethod.POST)
    public ResponseEntity<?> reportIssue(@RequestHeader("Authorization") String token,
                                         @RequestBody BookingIssueDTO bookingIssueDTO) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        
        // System.out.println(bookingIssueDTO)
        Booking booking = bookingRepo.findById(bookingIssueDTO.getId());
        
        // Get late users booking info to check if they are actually late.
        UserInfo lateUser;
        try {
            System.out.println(bookingIssueDTO.getRegno());
            Car car = carRepo.findByRegNo(bookingIssueDTO.getRegno());
            System.out.println("GET LATE USER");
            System.out.println(car.getUserId().getId());
            lateUser = userRepo.findById(car.getUserId().getId());
        } catch(EntityNotFoundException e) {
            ErrorDTO error = new ErrorDTO("Request", "Car registration does not exist");
            ErrorResponse er = errorService.createErrorResp(error, "Car registration does not exist");
            return ResponseEntity.ok(er);
        }
        
        LocalDateTime currentTime = LocalDateTime.now();
        List<Booking> lateUserBookings = bookingRepo.findByUserId(lateUser);
        Location loc = locationRepo.findByLocationId(bookingIssueDTO.getLocationId());
        boolean found = false;
        // Find booking for user that is late or is parked in wrong parking slot.
        for (int i = 0; i < lateUserBookings.size(); i++) {
            System.out.println(lateUserBookings.get(i).getEndDate());
            System.out.println(currentTime);
            System.out.println(lateUserBookings.get(i).getEndDate().isBefore(currentTime));
            System.out.println(booking.getCar().getRegNo());
            System.out.println(bookingIssueDTO.getRegno());
            if(lateUserBookings.get(i).getCar().getRegNo().equals(bookingIssueDTO.getRegno()) &&
               lateUserBookings.get(i).getEndDate().isBefore(currentTime) &&
               lateUserBookings.get(i).getLocationId().getLocationId() == booking.getLocationId().getLocationId()) {
                // When found, set as issue so security member will see.
                found = true;
                lateUserBookings.get(i).setIssue(true);
                bookingRepo.save(lateUserBookings.get(i));
                break;
            }
        }

        // Send an error to the user if the registration they have entered cannot be found for that reg number.
        if (found == false) {
            ErrorDTO error = new ErrorDTO("Request", "No user registered with that car registration at this car park spot");
            ErrorResponse er = errorService.createErrorResp(error, "No user registered with that car registration at this car park spot");
            return ResponseEntity.ok(er);
        }

        System.out.println("GET NEW BOOKING FOR USER");

        // find user new parking slot.
        // Check for current parking location.
        Booking newBooking = bookingService.getParkingSlot(loc, booking.getStartDate(), booking.getEndDate(), booking.getId());
        // Booking newBooking = bookingService.getParkingSlot(loc, booking); 
        
        // Give user choice to get money back or be given a new location.
        if ( newBooking.getParkingSlotId() == 0 ) {
            // Give money back
            user.setDeposit(user.getDeposit() + booking.getFee());
            return ResponseEntity.ok("Error finding place");
        }

        booking.setParkingSlotId(newBooking.getParkingSlotId());
        bookingRepo.save(booking);
        // bookingRepo.delete(booking);
        return ResponseEntity.ok("success"); 
    }

    
}