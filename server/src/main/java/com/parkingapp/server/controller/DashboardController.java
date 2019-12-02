package com.parkingapp.server.controller;

import java.util.ArrayList;

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
@CrossOrigin(origins = "https://localhost:8080")
public class DashboardController {

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @CrossOrigin(origins="https://localhost:8080")
    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ResponseEntity<DashboardLocationsDTO> dashboard(@RequestHeader("Authorization") String token) throws Exception {
        // Must convert loc values to DashboardLocationsDTO for JSON conversion.
        ArrayList<Location> loc = locationRepo.findByCountry("United Kingdom");
        
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
            
        }

        for (int ii = 0; ii < tmpLocations.size(); ii++) {
            System.out.println(tmpLocations.get(ii).toString());
        }

        // The DTO that's passed to front-end via JSON.
        DashboardLocationsDTO locations = new DashboardLocationsDTO();
        locations.setLocations(tmpLocations);
        
	    return ResponseEntity.ok(locations);
    }

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/dashboard/available", method = RequestMethod.POST)
    public ResponseEntity<?> availableParkingLocations(@RequestHeader("Authorization") String token, 
                                                       @RequestBody LocationsDistanceDTO clientDistances) throws Exception {
        System.out.println("TEST---------------------------------------------------------------------");
        System.out.println(clientDistances.toString());
        // System.out.println(clientDistances.getLocations().get(0).toString());
        LocationDTO dto = clientDistances.getLocations().get(1);
        // try {
            Location loc = locationRepo.findByLocationId(dto.getLocationId());
            System.out.println("LOCATION: " + loc.toString());
            ArrayList<Booking> booking = bookingRepo.findByLocationId(loc);
            System.out.println(booking.size());
            // System.out.println(booking);
            // for (int i = 0; i < booking.size(); i++) {
            //     System.out.println(booking.get(i).getLocationId());
            // }
        // } catch(Exception e) {
        //     System.out.println(e);
        // }
        //  = new Location(dto.getLocationId(), dto.getCountry(), dto.getCity(), dto.getAddress1(), dto.getAddress2(), dto.getPostcode(), dto.getLatitude(), dto.getLongitude());
        
        // System.out.println(booking.get(0).toString());
	    return ResponseEntity.ok(booking);
    }


    
}