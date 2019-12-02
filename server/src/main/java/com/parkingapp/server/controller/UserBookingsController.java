package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.List;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.ProfileDTO;
import com.parkingapp.server.domain.DTO.UserBookingDTO;
import com.parkingapp.server.domain.DTO.UserBookingsDTO;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;
import com.parkingapp.server.services.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserBookingsController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired UserInfoRepo userInfoRepo;
    
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/bookings/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> userBookings(@PathVariable("username") String username,
                                          @RequestHeader("Authorization") String token) throws Exception {
        // remove 'Bearer ' from Authorization header
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        // String username = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(usernameTok);
        UserInfo user = new UserInfo();
        user = userInfoRepo.findByUsername(usernameTok);
        if ( !username.equals(user.getUsername()) ) {
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorised access", new ErrorDTO()));
        }
        List<Booking> bookings = bookingRepo.findByUserId(user);

        
        ArrayList<UserBookingDTO> tmpBookings = new ArrayList<>();
        for (int i = 0; i < bookings.size(); i++) {
            LocationDTO location = new LocationDTO();
            UserBookingDTO booking = new UserBookingDTO();
            Booking temp = new Booking();
            temp = bookings.get(i);
            System.out.println("TEST");
            // System.out.println(temp.toString());
            booking.setId(temp.getId());
            booking.setUserId(temp.getUserId().getId());
            location.setLocationId(temp.getLocationId().getLocationId());
            location.setCountry(temp.getLocationId().getCountry());
            location.setCity(temp.getLocationId().getCity());
            location.setAddress1(temp.getLocationId().getAddress1());
            location.setAddress2(temp.getLocationId().getAddress2());
            location.setPostcode(temp.getLocationId().getPostcode());
            booking.setLocationId(location);
            booking.setStartDate(temp.getStartDate());
            booking.setEndDate(temp.getEndDate());
            booking.setActive(temp.isActive());
            System.out.println(booking.toString());
            tmpBookings.add(booking);
        }

        System.out.println("TEST");
        // System.out.println(tmpBookings.get(0).getLocationId().getCity());
        // System.out.println(tmpBookings.get(1).getLocationId().getCity());
        UserBookingsDTO userBookings = new UserBookingsDTO(tmpBookings);
        // Use profile DTO to pass information to frontend.
        // profileInfo.setUsername(user.getUsername());
        // profileInfo.setFirstname(user.getFirstname());
        // profileInfo.setLastname(user.getLastname());
        // profileInfo.setEmail(user.getEmail());
        // profileInfo.setCars(user.getCars());
	    return ResponseEntity.ok(userBookings);
	}
}