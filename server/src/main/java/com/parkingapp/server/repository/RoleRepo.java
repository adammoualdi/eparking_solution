package com.parkingapp.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parkingapp.server.domain.Role;

@Repository
public interface RoleRepo extends CrudRepository<Role, Integer> {

		public Role findById(int id);
		public Role findByRole(String role);
		
}
