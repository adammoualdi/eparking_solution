package com.parkingapp.server;

import static org.junit.Assert.*;

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

    @Test
    public void testFindRoleId() {
        entityManager.persist(new Role(1, "USER"));
        Role role = roleRepo.findById(1);
        assertEquals("USER", role.getRole());
    }

    @Test
    public void testFindByUsername() {
        // entityManager.persist(new Role(1, "USER"));
        entityManager.merge(new UserInfo(11, "usernametest", "password"));
        UserInfo user = userInfoRepo.findByUsername("usernametest");
        assertEquals("usernametest", user.getUsername());
    }
}