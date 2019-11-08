package com.parkingapp.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import com.parkingapp.server.domain.Location;

@Repository
public interface LocationRepo extends CrudRepository<Location, Integer> {
//		User findById(int id);
//		User findUserByEmail(String email);
	    Location findByCity(String city);
	    // UserInfo findByUsernameAndPassword(String username, String password);
	    ArrayList<Location> findByCountry(String country);
}
