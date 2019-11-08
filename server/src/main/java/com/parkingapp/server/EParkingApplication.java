package com.parkingapp.server;

import java.time.LocalDateTime;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.Location;
// import com.parkingapp.server.domain.LocationTest;
import com.parkingapp.server.domain.Role;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.CarRepository;
import com.parkingapp.server.repository.LocationRepo;
// import com.parkingapp.server.repository.LocationTestRepo;
import com.parkingapp.server.repository.RoleRepo;
import com.parkingapp.server.repository.UserInfoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EParkingApplication implements CommandLineRunner {

	@Autowired UserInfoRepo userRepo;
	@Autowired RoleRepo roleRepo;
	@Autowired LocationRepo locRepo;
	@Autowired CarRepository carRepo;
	@Autowired BookingRepository bookingRepo;

	BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();


	public final static int USER = 1;
	public final static int ADMIN = 2;
	public final static int TEST = 3;

	public static void main(String[] args) {
		SpringApplication.run(EParkingApplication.class, args);
	}

	public void run(String... args) {
		
		UserInfo user2 = new UserInfo();
		user2.setId(1);
		user2.setUsername("username1");
		user2.setPassword(pe.encode("password123"));
		user2.setRole(new Role(USER, "User"));
		userRepo.save(user2);

		UserInfo user4 = new UserInfo();
		user4.setId(12);
		user4.setUsername("username123");
		user4.setPassword(pe.encode("password"));
		user4.setRole(new Role(USER, "user"));
		userRepo.save(user4);

		UserInfo user = new UserInfo();
		// user.setId();
		user.setUsername("adminuser");
		user.setPassword(pe.encode("password"));
		user.setRole(new Role(ADMIN, "Admin"));
		userRepo.save(user);

		UserInfo user3 = new UserInfo();
		user3.setId(11);
		user3.setUsername("testuser");
		user3.setPassword(pe.encode("password"));
		user3.setRole(new Role(TEST, "test"));
		userRepo.save(user3);

		Location loc = new Location("country", "city", "address1", "address2", "postcode");
		locRepo.save(loc);

		Car car = new Car(20, user2,"reg", "model");
		carRepo.save(car);

		Booking booking1 = new Booking();
		booking1.setId(22);
		booking1.setLocationId(loc);
		booking1.setUserId(user2);
		booking1.setStartDate(LocalDateTime.now());
		bookingRepo.save(booking1);
		
		// Car car2 = new Car(2,user3,"reg2", "model2");
		// carRepo.save(car2);
		// LocationTest opLoc1 = new LocationTest();
		// LocationTest opLoc2 = new LocationTest();
		// LocationTest opLoc3 = new LocationTest();
		 
		//  opLoc1.setRegionId("UK");
		//  opLoc2.setRegionId("UK");
		//  opLoc3.setRegionId("UK");
		 
		//  opLoc1.setId("MAN");
		//  opLoc2.setId("LON");
		//  opLoc3.setId("GLA");
		 
		//  opLoc1.setName("Manchester");
		//  opLoc2.setName("London");
		//  opLoc3.setName("Glasgow");
		 
		//  locationRepo.save(opLoc1);
		//  locationRepo.save(opLoc2);
		//  locationRepo.save(opLoc3);
		
	}

}
