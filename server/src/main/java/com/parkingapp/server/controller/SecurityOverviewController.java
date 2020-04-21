package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.CarDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.LocationsDistanceDTO;
import com.parkingapp.server.domain.DTO.SecurityOverviewDTO;
import com.parkingapp.server.domain.DTO.UserDTO;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;

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
public class SecurityOverviewController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('SECURITY')")
	@RequestMapping(value = "/dashboard/security/bookings", method = RequestMethod.POST)
    public ResponseEntity<?> getBookings(@RequestHeader("Authorization") String token) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);

        Set<Location> userLocs = user.getLocationsPermission();

        ArrayList<BookingDTO> bookings = new ArrayList<BookingDTO>();

        Iterator<Location> it = userLocs.iterator();
        // For each of the security user's locations
        while(it.hasNext()) {
            Location loc = it.next();
            ArrayList<Booking> tmpbookings = bookingRepo.findByLocationId(loc);
            ArrayList<BookingDTO> listDTO = new ArrayList<BookingDTO>();
            // Put information into DTOs
            for (int i = 0; i < tmpbookings.size(); i++) {
                UserInfo bookingUser = userRepo.findByUsername(tmpbookings.get(i).getUserId().getUsername());
                BookingDTO bookingDTO = new BookingDTO();
                UserDTO userDTO = new UserDTO();
                userDTO.setFirstname(bookingUser.getFirstname());
                userDTO.setLastname(bookingUser.getLastname());
                userDTO.setEmail(bookingUser.getEmail());
                userDTO.setUsername(bookingUser.getUsername());
                userDTO.setId(bookingUser.getId());
                bookingDTO.setUserDTO(userDTO);
                CarDTO carDTO = new CarDTO();
                carDTO.setId(tmpbookings.get(i).getCar().getCarId());
                carDTO.setModel(tmpbookings.get(i).getCar().getModel());
                carDTO.setRegNo(tmpbookings.get(i).getCar().getRegNo());
                bookingDTO.setCar(carDTO);
                bookingDTO.setId(tmpbookings.get(i).getId());
                bookingDTO.setStartDate(tmpbookings.get(i).getStartDate());
                bookingDTO.setEndDate(tmpbookings.get(i).getEndDate());
                bookingDTO.setActive(tmpbookings.get(i).isActive());
                bookingDTO.setParkingSlotId(tmpbookings.get(i).getParkingSlotId());
                bookingDTO.setParkingConfirmation(tmpbookings.get(i).isParkingConfirmation());
                bookingDTO.setUsername(tmpbookings.get(i).getUserId().getUsername());
                bookingDTO.setIssue(tmpbookings.get(i).isIssue());
                LocationDTO locDTO = new LocationDTO();
                locDTO.setAddress1(loc.getAddress1());
                locDTO.setAddress2(loc.getAddress2());
                locDTO.setCity(loc.getCity());
                locDTO.setCountry(loc.getCountry());
                locDTO.setPostcode(loc.getPostcode());
                locDTO.setLongitude(loc.getLongitude());
                locDTO.setLatitude(loc.getLatitude());
                locDTO.setLocationId(loc.getLocationId());
                bookingDTO.setLocationId(locDTO);
                listDTO.add(bookingDTO);       
            }
            // If no bookings for specific location
            // if (tmpbookings.size() == 0) {
            //     BookingDTO bookingDTO = new BookingDTO();
            //     LocationDTO locDTO = new LocationDTO();
            //     locDTO.setAddress1(loc.getAddress1());
            //     locDTO.setAddress2(loc.getAddress2());
            //     locDTO.setCity(loc.getCity());
            //     locDTO.setCountry(loc.getCountry());
            //     locDTO.setPostcode(loc.getPostcode());
            //     locDTO.setLongitude(loc.getLongitude());
            //     locDTO.setLatitude(loc.getLatitude());
            //     locDTO.setLocationId(loc.getLocationId());
            //     bookingDTO.setLocationId(locDTO);
            //     listDTO.add(bookingDTO); 
            // }
            bookings.addAll(listDTO);
        }

        SecurityOverviewDTO output = new SecurityOverviewDTO(bookings);

        // LocationsDistanceDTO output = new LocationsDistanceDTO();
        // output.setLocations(locationsArray);

        return ResponseEntity.ok(output);
    }

    @PreAuthorize("hasRole('SECURITY')")
	@RequestMapping(value = "/dashboard/security/bookings/update", method = RequestMethod.GET)
    public ResponseEntity<?> checkParked(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok("success");
    }
    
    @PreAuthorize("hasRole('SECURITY')")
	@RequestMapping(value = "/dashboard/security/bookings/issue", method = RequestMethod.POST)
    public ResponseEntity<?> confirmIssue(@RequestHeader("Authorization")
                                          @RequestBody BookingDTO bookingDTO, 
                                          String token) throws Exception {
        Booking booking = bookingRepo.findById(bookingDTO.getId());
        UserInfo user = userRepo.findById(booking.getUserId().getId());
        user.setDeposit(user.getDeposit() - booking.getDepositFee());
        userRepo.save(user);
        return ResponseEntity.ok("success");
    }
}