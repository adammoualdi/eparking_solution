package com.parkingapp.server.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.DashboardLocationDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.LocationsDistanceDTO;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.BookingCheckerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DashboardController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @Autowired BookingCheckerService bookingService;

    // Get all locations
    // @CrossOrigin(origins="https://localhost:8080")
    // @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ResponseEntity<DashboardLocationsDTO> dashboard(@RequestHeader("Authorization") String token) throws Exception {
        // Must convert loc values to DashboardLocationsDTO for JSON conversion.
        Iterable<Location> loc = locationRepo.findAll();
        Iterator<Location> iter = loc.iterator();
        
        // Be able to store loc values into new list with DTO object.
        ArrayList<DashboardLocationDTO> tmpLocations = new ArrayList<>();
        // loop through database values and assign them to DTO (DashboardLocationDTO).
        // for (int i = 0; i < loc.size(); i++) {
        while(iter.hasNext()) {
            DashboardLocationDTO location = new DashboardLocationDTO();
            Location temp = new Location();
            // temp = loc.get(i);
            temp = iter.next();

            if (temp.getApproved() == true) {
                location.setId(temp.getLocationId());
                location.setAddress1(temp.getAddress1());
                location.setAddress2(temp.getAddress2());
                location.setCity(temp.getCity());
                location.setCountry(temp.getCountry());
                location.setPostcode(temp.getPostcode());
                location.setLatitude(temp.getLatitude());
                location.setLongitude(temp.getLongitude());
                // add DTO to temp array of that DTO object - to be returned.
                tmpLocations.add(location);
            }

            // System.out.println(temp.toString());
            
            // System.out.println(tmpLocations);
            
        }

        // The DTO that's passed to front-end via JSON.
        DashboardLocationsDTO locations = new DashboardLocationsDTO();
        locations.setLocations(tmpLocations);
        
	    return ResponseEntity.ok(locations);
    }

    @PreAuthorize("hasRole('OWNER')")
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public ResponseEntity<DashboardLocationsDTO> ownerLocations(@RequestHeader("Authorization") String token) throws Exception {
                
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        ArrayList<Location> loc = locationRepo.findByPermissions(user);
        // ArrayList<Location> test = locationRepo.findByCountry("United Kingdom");
        
        // Be able to store loc values into new list with DTO object.
        ArrayList<DashboardLocationDTO> tmpLocations = new ArrayList<>();
        // loop through database values and assign them to DTO (DashboardLocationDTO).
        for (int i = 0; i < loc.size(); i++) {
            DashboardLocationDTO location = new DashboardLocationDTO();
            Location temp = new Location();
            temp = loc.get(i);
            location.setId(temp.getLocationId());
            location.setAddress1(temp.getAddress1());
            location.setAddress2(temp.getAddress2());
            location.setCity(temp.getCity());
            location.setCountry(temp.getCountry());
            location.setPostcode(temp.getPostcode());
            location.setLatitude(temp.getLatitude());
            location.setLongitude(temp.getLongitude());
            // add DTO to temp array of that DTO object - to be returned.
            tmpLocations.add(location);

            // System.out.println(temp.toString());
            
            // System.out.println(tmpLocations);
            // test.get(1).setUserId(user);
            // System.out.println(test.get(1).toString());
            // locationRepo.save(test.get(1));
        }

        for (int ii = 0; ii < tmpLocations.size(); ii++) {
            System.out.println(tmpLocations.get(ii).toString());
        }

        // The DTO that's passed to front-end via JSON.
        DashboardLocationsDTO locations = new DashboardLocationsDTO();
        locations.setLocations(tmpLocations);
        
	    return ResponseEntity.ok(locations);
    }


    // PUT FAKE BOOKINGS INSIDE
    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/dashboard/available", method = RequestMethod.POST)
    public ResponseEntity<?> availableParkingLocations(@RequestHeader("Authorization") String token, 
                                                       @RequestBody LocationsDistanceDTO clientDistances) throws Exception {
        System.out.println("TEST---------------------------------------------------------------------");
        System.out.println(clientDistances.toString());
        // LocationsDistanceDTO tmp = clientDistances;
        System.out.println(clientDistances.getArriveTime());
        System.out.println(clientDistances.getLeavingTime());
        System.out.println("GET BOOKINGS FOR THAT LOCATION");

        System.out.println("Amount of locations: " + clientDistances.getLocations().size());
        ArrayList<LocationDTO> locationsArray = new ArrayList<>();

        for (int i = 0; i < clientDistances.getLocations().size(); i++) {
            Location loc = locationRepo.findByLocationId(clientDistances.getLocations().get(i).getLocationId());
            // ArrayList<Booking> bookings = bookingRepo.findBookings(loc, clientDistances.getArriveTime(), clientDistances.getLeavingTime());
            // Collections.sort(bookings);
            // System.out.println("SIZE OF BOOKING ARRAY " + bookings.size());
            Booking booking = bookingService.getParkingSlot(loc, clientDistances.getArriveTime(), clientDistances.getLeavingTime(), 0);
            if ( booking.getParkingSlotId() == 0) {
                System.out.println("OPTIMISING PARKING SLOTS");
                booking = bookingService.optimiseParkingSlots(loc, clientDistances.getArriveTime(), clientDistances.getLeavingTime(), 0);
                if (booking.getParkingSlotId() == 0) {
                    break;
                }
            }
            System.out.println("PARKING SLOT ID " + booking.getParkingSlotId());
            // boolean available = false;
            // if (bookings.size() != 0) {
            //     if (bookings.get(bookings.size()-1).getParkingSlotId() < loc.getSpaces()) {
            //         available = true;
            //     }
            // } else {
            //     available = true;
            // }
            // if (available == true) {
            LocationDTO location = new LocationDTO();
            location.setLocationId(clientDistances.getLocations().get(i).getLocationId());
            location.setAddress1(clientDistances.getLocations().get(i).getAddress1());
            location.setAddress2(clientDistances.getLocations().get(i).getAddress2());
            location.setCountry(clientDistances.getLocations().get(i).getCountry());
            location.setCity(clientDistances.getLocations().get(i).getCity());
            location.setLatitude(clientDistances.getLocations().get(i).getLatitude());
            location.setLongitude(clientDistances.getLocations().get(i).getLongitude());
            location.setPostcode(clientDistances.getLocations().get(i).getPostcode());
            location.setDistance(clientDistances.getLocations().get(i).getDistance());
            location.setArriveTime(clientDistances.getLocations().get(i).getArriveTime());
            location.setLeavingTime(clientDistances.getLocations().get(i).getLeavingTime());

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            LocalDateTime startTime = LocalDateTime.parse(clientDistances.getLocations().get(i).getArriveTime(), inputFormatter);
            LocalDateTime endTime = LocalDateTime.parse(clientDistances.getLocations().get(i).getLeavingTime(), inputFormatter);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            // LocalDateTime dateTime = LocalDateTime.parse("2016-09-21 13:43:27.000", formatter);
            // Calc overall cost for booking
            // Period p = new Period(startTime, endTime);
            double minutes = ChronoUnit.MINUTES.between(startTime, endTime);
            double hours = minutes / 60;
            // long minutes = p.getMinutes();
            double costForBooking = hours * loc.getCostPerHour();
            location.setDeposit(costForBooking);
            BigDecimal bd = new BigDecimal(costForBooking * 0.8).setScale(2, RoundingMode.HALF_UP);
            location.setDepositFee(bd.doubleValue());

            System.out.println("SPACE AVAILABLE FOR PARTICULAR BOOKING");
            System.out.println("ADD TO OUTPUT");
            locationsArray.add(location);
            // }
        }

        LocationsDistanceDTO output = new LocationsDistanceDTO();
        output.setLocations(locationsArray);

	    return ResponseEntity.ok(output);
    }


    
}