package com.parkingapp.server;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
		user2.setFirstname("Adam");
		user2.setLastname("Moualdi");
		user2.setEmail("adammoualdi@hotmail.co.uk");
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

		// List<Booking> bookings = new ArrayList<>();

		Location loc = new Location("United Kingdom", "Leicester", "address1", "address2", "LE1 7RH", 52.619497522, -1.121332848, 2);
		locRepo.save(loc);

		Location loc1 = new Location("United Kingdom", "Sheffield", "address1", "address2", "LE1 7RH", 53.37701, -1.46814, 5);
		locRepo.save(loc1);

		Car car = new Car(20, user2,"reg", "model");
		carRepo.save(car);

		Booking booking1 = new Booking();
		booking1.setId(22);
		booking1.setLocationId(loc);
		booking1.setUserId(user2);
		booking1.setStartDate(LocalDateTime.now());
		booking1.setActive(true);
		bookingRepo.save(booking1);

		// bookings.add(booking1);
		
		// Car car2 = new Car(2,user3,"reg2", "model2");
		// carRepo.save(car2);
		
	}

}
