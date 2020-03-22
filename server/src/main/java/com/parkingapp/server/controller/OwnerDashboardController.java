package com.parkingapp.server.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.BookingDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationDTO;
import com.parkingapp.server.domain.DTO.DashboardLocationsDTO;
import com.parkingapp.server.domain.DTO.LocationDTO;
import com.parkingapp.server.domain.DTO.LocationsDistanceDTO;
import com.parkingapp.server.domain.DTO.OwnerOverviewDTO;
import com.parkingapp.server.domain.DTO.OwnerOverviewsDTO;
import com.parkingapp.server.domain.DTO.UserBookingDTO;
import com.parkingapp.server.domain.DTO.UserBookingsDTO;
import com.parkingapp.server.domain.DTO.UserDTO;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.UserInfoRepo;
import com.parkingapp.server.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class OwnerDashboardController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired LocationRepo locationRepo;
    @Autowired UserInfoRepo userRepo;
    @Autowired BookingRepository bookingRepo;

    @PreAuthorize("hasRole('OWNER')")
	@RequestMapping(value = "/locations/owners", method = RequestMethod.GET)
	public ResponseEntity<DashboardLocationsDTO> ownerLocations(@RequestHeader("Authorization") String token) throws Exception {
                
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        ArrayList<Location> loc = locationRepo.findByPermissions(user);
        // Set<Location> loc = locationRepo.findByPermissions(user);
        
        // Be able to store loc values into new list with DTO object.
        ArrayList<DashboardLocationDTO> tmpLocations = new ArrayList<>();
        // loop through database values and assign them to DTO (DashboardLocationDTO).
        for (int i = 0; i < loc.size(); i++) {
            DashboardLocationDTO location = new DashboardLocationDTO();
            Location temp = new Location();
            temp = loc.get(i);
            // check if location has been approved
            // if (temp.getApproved() == true) {
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
            // }
        }

        for (int ii = 0; ii < tmpLocations.size(); ii++) {
            System.out.println(tmpLocations.get(ii).toString());
        }

        // The DTO that's passed to front-end via JSON.
        DashboardLocationsDTO locations = new DashboardLocationsDTO();
        locations.setLocations(tmpLocations);
        
	    return ResponseEntity.ok(locations);
    }

    @PreAuthorize("hasRole('OWNER')")
	@RequestMapping(value = "/locations/add", method = RequestMethod.POST)
    public ResponseEntity<?> addParkingLocation(@RequestHeader("Authorization") String token, 
                                                @RequestBody Location location) throws Exception {
        
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
                                
        // location.setUserId(user);
        Set<Location> loc = user.getLocationsPermission();
        loc.add(location);
        user.setLocationsPermission(loc);
        // userRepo.save(user);
        Set<UserInfo> userLoc = new HashSet<UserInfo>();
        userLoc.add(user);
        System.out.println(location.isSensors());
        System.out.println(location.toString());
        locationRepo.save(location);
	    return ResponseEntity.ok(location);
    }

    @PreAuthorize("hasRole('OWNER')")
    @RequestMapping(value = "/locations/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> upload(HttpServletRequest request,
                                         @RequestHeader("Authorization") String token,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("name") String uniqueId) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        // UserInfo user = userRepo.findByUsername(usernameTok);
        System.out.println(uniqueId);
        String absolutePathToImages = request.getSession().getServletContext().getRealPath("/");
        if (file.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        } else {
            System.out.println(absolutePathToImages);
            File dest = new File(absolutePathToImages + usernameTok + uniqueId);
            file.transferTo(dest);
            System.out.println("GOT IMAGE!!!!!!!!!!!!!!!!!!");
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
    }


    @PreAuthorize("hasRole('OWNER')")
	@RequestMapping(value = "/locations/bookings", method = RequestMethod.POST)
    public ResponseEntity<?> getLocationBookings(@RequestHeader("Authorization") String token,
                                                 @RequestBody Location location) throws Exception {

        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userRepo.findByUsername(usernameTok);
        
        System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println(location.getLocationId());
        Location locSearch = locationRepo.findByLocationId(location.getLocationId());
        ArrayList<Booking> bookings = bookingRepo.findByLocationId(locSearch);
                                                    
        ArrayList<OwnerOverviewDTO> outputList = new ArrayList<OwnerOverviewDTO>();
        for (int i = 0; i < bookings.size(); i++){
            OwnerOverviewDTO booking = new OwnerOverviewDTO();
            BookingDTO bookingDTO = new BookingDTO();
            UserDTO userDTO = new UserDTO();
            System.out.println(bookings.get(i).getId());
            UserInfo bookingUser = userRepo.findById(bookings.get(i).getUserId().getId());
            bookingDTO.setId(bookings.get(i).getId());
            System.out.println(bookingUser.getFirstname());
            bookingDTO.setStartDate(bookings.get(i).getStartDate());
            bookingDTO.setEndDate(bookings.get(i).getEndDate());
            bookingDTO.setActive(bookings.get(i).isActive());
            userDTO.setFirstname(bookingUser.getFirstname());
            userDTO.setLastname(bookingUser.getLastname());
            userDTO.setEmail(bookingUser.getEmail());
            userDTO.setUsername(bookingUser.getUsername());
            booking.setBooking(bookingDTO);
            booking.setUser(userDTO);
            outputList.add(booking);
        }

        OwnerOverviewsDTO output = new OwnerOverviewsDTO(outputList);
        // location.setUserId(user);
        // System.out.println(location.isSensors());
        // System.out.println(location.toString());
        // locationRepo.save(location);
	    return ResponseEntity.ok(output);
    }
    
    // @RequestMapping(value = "/locations/image", method = RequestMethod.GET, produces = "image/jpg")
    //     public @ResponseBody byte[] getFile()  {
    //         try {
    //             // Retrieve image from the classpath.
    //             InputStream is = this.getClass().getResourceAsStream("/test.jpg"); 

    //             // Prepare buffered image.
    //             BufferedImage img = ImageIO.read(is);

    //             // Create a byte array output stream.
    //             ByteArrayOutputStream bao = new ByteArrayOutputStream();

    //             // Write to output stream
    //             ImageIO.write(img, "jpg", bao);

    //             return bao.toByteArray();
    //         } catch (IOException e) {
    //             throw new RuntimeException(e);
    //         }
    //     }
    
}