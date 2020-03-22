package com.parkingapp.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Set;

import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;

@Repository
public interface LocationRepo extends CrudRepository<Location, Integer> {
		Location findByLocationId(int id);
//		User findUserByEmail(String email);
		Location findByCity(String city);
		ArrayList<Location> findByPostcode(String postcode);
	    // UserInfo findByUsernameAndPassword(String username, String password);
		ArrayList<Location> findByCountry(String country);
		// ArrayList<Location> findByUserId(UserInfo user);
		ArrayList<Location> findByApproved(boolean approved);
		ArrayList<Location> findByPermissions(UserInfo user);
}
