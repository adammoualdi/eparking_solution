package com.parkingapp.server;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Car;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.Role;
import com.parkingapp.server.domain.UserInfo;
import com.parkingapp.server.repository.BookingRepository;
import com.parkingapp.server.repository.CarRepository;
import com.parkingapp.server.repository.LocationRepo;
import com.parkingapp.server.repository.RoleRepo;
import com.parkingapp.server.repository.UserInfoRepo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EParkingAppTestJPA {

    @Autowired
    private TestEntityManager entityManager;

	@Autowired
	private RoleRepo roleRepo;
		
	@Autowired
	private UserInfoRepo userInfoRepo;
	
	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private CarRepository carRepo;
	
	@Autowired
	private LocationRepo locationRepo;

    // @Test
    // public void testFindRoleId() {
    //     entityManager.persist(new Role(1, "USER"));
    //     Role role = roleRepo.findById(1);
    //     assertEquals("USER", role.getRole());
    // }

    // @Test
    // public void testFindByUsername() {
    //     // entityManager.persist(new Role(1, "USER"));
    //     entityManager.merge(new UserInfo(11, "usernametest", "password"));
    //     UserInfo user = userInfoRepo.findByUsername("usernametest");
    //     assertEquals("usernametest", user.getUsername());
    // }

    @Test
    public void testFindByUserBookings() {
		entityManager.flush();
		Set<Location> locations = new HashSet<Location>(); 
        entityManager.persist(new Role(1, "USER"));
        UserInfo user2 = new UserInfo();
		user2.setId(1);
		user2.setUsername("username1");
		user2.setFirstname("Adam");
		user2.setLastname("Moualdi");
		user2.setEmail("adammoualdi@hotmail.co.uk");
		user2.setPassword("password123");
		LocalDate myDate3 = LocalDate.of(1998, 6, 10);
		user2.setDofb(myDate3);
		// user2.setRole(new Role(USER, "User"));
		user2.setDeposit(20.00);
		user2.setRequiredDeposit(8);
        // userRepo.save(user2);


        Location loc1 = new Location("United Kingdom", "Sheffield", "Sheffield Train Station", "Sheaf Street", "S1 2BP", 53.37701, -1.46814, 3);
        entityManager.merge(loc1);
		
		locations.add(loc1);
		user2.setLocationsPermission(locations);

		entityManager.merge(user2);

		Car car = new Car(user2, "regNo", "model");
        entityManager.merge(car);
		
		Booking booking1 = new Booking();
		booking1.setId(22);
		booking1.setLocationId(loc1);
		booking1.setUserId(user2);
		booking1.setIssue(false);
		booking1.setCompleted(false);
		String str = "2020-05-05 09:00";
		String str1 = "2020-05-05 14:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		LocalDateTime dateTime1 = LocalDateTime.parse(str1, formatter);
		booking1.setStartDate(dateTime);
		booking1.setEndDate(dateTime1);
		booking1.setActive(true);
		booking1.setParkingSlotId(1);
		booking1.setBookingUrl("test");
		booking1.setCar(car);
		booking1.setFee(10);
		// double depositFee = 10 * 0.8;
		BigDecimal bd = new BigDecimal(10 * 0.8).setScale(2, RoundingMode.HALF_UP);
        booking1.setDepositFee(bd.doubleValue());
        entityManager.merge(booking1);
		// bookingRepo.save(booking1);
		List<Booking> bookings = bookingRepo.findBookings(loc1, dateTime, dateTime1, 0);
		assertEquals(bookings.size(), 1);
        // assertEquals("usernametest", user.g;
    }
}