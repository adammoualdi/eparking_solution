package com.parkingapp.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkingapp.server.domain.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
//		User findById(int id);
//		User findUserByEmail(String email);
	    Booking findById(int id);
	    // Car findByUsernameAndPassword(String username, String password);
//	    ArrayList<UserInfo> findByRoleId(int roleId);
}
