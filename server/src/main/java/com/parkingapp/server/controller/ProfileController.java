package com.parkingapp.server.controller;

import java.util.ArrayList;
import java.util.List;

import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.ErrorDTO;
import com.parkingapp.server.domain.ErrorResponse;
import com.parkingapp.server.domain.JwtResponse;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.domain.DTO.CarDTO;
import com.parkingapp.server.domain.DTO.CarsDTO;
import com.parkingapp.server.domain.DTO.ProfileDTO;
import com.parkingapp.server.repository.CarRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProfileController {

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired UserInfoRepo userInfoRepo;
    @Autowired CarRepository carRepo;

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> profileInfo(@PathVariable("username") String username, 
                                         @RequestHeader("Authorization") String token) throws Exception {
        // remove 'Bearer ' from Authorization header
        // String username = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userInfoRepo.findByUsername(usernameTok);

        if ( !username.equals(user.getUsername()) ) {
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorised access", new ErrorDTO()));
        }
        // Use profile DTO to pass information to frontend.
        ProfileDTO profileInfo = new ProfileDTO();
        profileInfo.setUsername(user.getUsername());
        profileInfo.setFirstname(user.getFirstname());
        profileInfo.setLastname(user.getLastname());
        profileInfo.setEmail(user.getEmail());
        profileInfo.setDofb(user.getDofb());


        List<Car> cars = carRepo.findByUserId(user);
        List<CarDTO> carList = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            Car temp = cars.get(i);
            CarDTO car = new CarDTO(temp.getCarId(), temp.getRegNo(), temp.getModel());
            carList.add(car);
        }

        CarsDTO carsOutput = new CarsDTO(carList);
        profileInfo.setCars(carsOutput);
        System.out.println(profileInfo.toString());
        // profileInfo.setCars(user.getCars());
	    return ResponseEntity.ok(profileInfo);
    }

    @PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/profile/edit/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> profileEditInfo(@PathVariable("username") String username, 
                                             @RequestHeader("Authorization") String token,
                                             @RequestBody ProfileDTO profileInfo) throws Exception {
        
        String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
        UserInfo user = userInfoRepo.findByUsername(usernameTok);

        if ( !username.equals(user.getUsername()) ) {
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorised access", new ErrorDTO()));
        }
        
        user.setFirstname(profileInfo.getFirstname());
        user.setLastname(profileInfo.getLastname());
        user.setEmail(profileInfo.getEmail());
        user.setDofb(profileInfo.getDofb());
        
        List<Car> cars = carRepo.findByUserId(user);
        // List<Car> carList = new ArrayList<>();
        // To add cars
        // for (int i = 0; i < profileInfo.getCars().getCars().size(); i++) {
        //     Car car = new Car();
        //     if (profileInfo.getCars().getCars().get(i).isAdd()) {
        //         car.setModel(profileInfo.getCars().getCars().get(i).getModel());
        //         car.setRegNo(profileInfo.getCars().getCars().get(i).getRegNo());
        //         car.setUserId(user);
        //         carList.add(car);
        //     }       
        // }
        carRepo.saveAll(cars);
        userInfoRepo.save(user);

        // CarsDTO carsOutput = new CarsDTO(carList);
        // profileInfo.setCars(carsOutput);
        System.out.println(profileInfo.toString());
        // profileInfo.setCars(user.getCars());
	    return ResponseEntity.ok(profileInfo);
    }
    
    // @PreAuthorize("hasRole('USER')")
	// @RequestMapping(value = "/profile/user", method = RequestMethod.GET)
    // public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) throws Exception {
        
    //     String usernameTok = jwtTokenUtil.getUsernameFromToken(token.substring(7,token.length()));
    //     UserInfo user = userInfoRepo.findByUsername(usernameTok);

    //     List<Car> userCars = carRepo.findByUserId(user);
    //     List<CarDTO> tmpCars = new ArrayList<CarDTO>();
    //     for (int i = 0; i < userCars.size(); i++) {
    //         CarDTO car = new CarDTO();
    //         car.setId(userCars.get(i).getCarId());
    //         car.setModel(userCars.get(i).getModel());
    //         car.setRegNo(userCars.get(i).getRegNo());
    //         tmpCars.add(car);
    //     }

    //     CarsDTO carsDTO = new CarsDTO(tmpCars);
        
    //     return ResponseEntity.ok(carsDTO);
    // }
}