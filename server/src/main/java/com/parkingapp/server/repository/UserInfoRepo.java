package com.parkingapp.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import com.parkingapp.server.domain.Booking;
import com.parkingapp.server.domain.UserInfo;

@Repository
public interface UserInfoRepo extends CrudRepository<UserInfo, Integer> {
//		User findById(int id);
//		User findUserByEmail(String email);
	    UserInfo findByUsername(String username);
	    UserInfo findByUsernameAndPassword(String username, String password);
//	    ArrayList<UserInfo> findByRoleId(int roleId);
}
