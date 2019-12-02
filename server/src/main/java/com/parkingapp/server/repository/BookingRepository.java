package com.parkingapp.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
//		User findById(int id);
//		User findUserByEmail(String email);
		Booking findById(int id);
		ArrayList<Booking> findByUserId(UserInfo user);
		ArrayList<Booking> findByLocationId(Location location);
		// @Query("SELECT t.title FROM Todo t where t.id = :id") 
		// ArrayList<Booking> findByUserId(@Param("id") int id);
	    // Car findByUsernameAndPassword(String username, String password);
//	    ArrayList<UserInfo> findByRoleId(int roleId);
}
