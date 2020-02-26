package com.parkingapp.server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class EParkingApplication implements CommandLineRunner {

	@Autowired UserInfoRepo userRepo;
	@Autowired RoleRepo roleRepo;
	@Autowired LocationRepo locRepo;
	@Autowired CarRepository carRepo;
	@Autowired BookingRepository bookingRepo;

	public final static int USER = 1;
	public final static int ADMIN = 2;
	public final static int OWNER = 3;
	public final static int SECURITY = 4;
	public final static int TEST = 5;

	public static void main(String[] args) {
		SpringApplication.run(EParkingApplication.class, args);
	}

	public void run(String... args) {

		BCryptPasswordEncoder pe = new  BCryptPasswordEncoder();

		UserInfo user2 = new UserInfo();
		user2.setId(1);
		user2.setUsername("username1");
		user2.setFirstname("Adam");
		user2.setLastname("Moualdi");
		user2.setEmail("adammoualdi@hotmail.co.uk");
		user2.setPassword(pe.encode("password123"));
		LocalDate myDate3 = LocalDate.of(1998, 6, 10);
		user2.setDofb(myDate3);
		user2.setRole(new Role(USER, "User"));
		userRepo.save(user2);

		UserInfo user4 = new UserInfo();
		user4.setId(12);
		user4.setUsername("username123");
		user4.setPassword(pe.encode("password"));
		user4.setRole(new Role(USER, "User"));
		userRepo.save(user4);

		UserInfo user = new UserInfo();
		// user.setId();
		user.setUsername("adminuser");
		user.setPassword(pe.encode("password"));
		user.setRole(new Role(ADMIN, "Admin"));
		userRepo.save(user);

		UserInfo security = new UserInfo();
		// user.setId();
		security.setUsername("security");
		security.setPassword(pe.encode("password"));
		security.setRole(new Role(SECURITY, "Security"));
		security.setDefaultPassword(true);
		userRepo.save(security);

		UserInfo user3 = new UserInfo();
		// user3.setId(11);
		user3.setUsername("owneruser");
		user3.setPassword(pe.encode("password"));
		user3.setFirstname("Ben");
		user3.setLastname("Neale");
		user3.setEmail("BenNeale@hotmail.co.uk");
		user3.setRole(new Role(OWNER, "Owner"));
		// userRepo.save(user3);

		// List<Booking> bookings = new ArrayList<>();
		Location loc = new Location("United Kingdom", "Leicester", "address1", "address2", "LE1 7RH", 52.619497522, -1.121332848, 2);
		loc.setUserId(user3);	
		// loc.setUserId();
		loc.setApproved(false);
		locRepo.save(loc);

		// loc.setUserId(user3);
		// locRepo.save(loc);

		Location loc1 = new Location("United Kingdom", "Sheffield", "Sheffield Train Station", "Sheaf Street", "S1 2BP", 53.37701, -1.46814, 5);
		// loc1.setUserId(user);
		loc1.setApproved(true);
		locRepo.save(loc1);

		
		// locRepo.save(loc1);

		Car car = new Car(20, user2,"reg", "model");
		carRepo.save(car);

		Car car1 = new Car(2, user2,"SE24QWR", "Audi TT");
		carRepo.save(car1);

		Booking booking1 = new Booking();
		booking1.setId(22);
		booking1.setLocationId(loc1);
		booking1.setUserId(user2);
		booking1.setStartDate(LocalDateTime.now());
		String str = "2020-01-10 12:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		booking1.setEndDate(dateTime);
		booking1.setActive(true);
		bookingRepo.save(booking1);

		// bookings.add(booking1);
		
		// Car car2 = new Car(2,user3,"reg2", "model2");
		// carRepo.save(car2);
		
	}

}
