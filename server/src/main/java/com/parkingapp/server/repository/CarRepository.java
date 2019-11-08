package com.parkingapp.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkingapp.server.domain.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
//		User findById(int id);
//		User findUserByEmail(String email);
	    Car findById(int id);
	    // Car findByUsernameAndPassword(String username, String password);
//	    ArrayList<UserInfo> findByRoleId(int roleId);
}
