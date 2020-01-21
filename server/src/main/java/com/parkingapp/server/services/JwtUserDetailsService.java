package com.parkingapp.server.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parkingapp.server.repository.UserInfoRepo;

import com.parkingapp.server.EParkingApplication;

import com.parkingapp.server.domain.UserInfo;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	 
	@Autowired UserInfoRepo userInfoRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		UserInfo loginUser = userInfoRepo.findByUsername(username);

		if (loginUser.getUsername() != null) {
			return new User(loginUser.getUsername(), 
							loginUser.getPassword(),
							enabled, 
							accountNonExpired, 
							credentialsNonExpired, 
							accountNonLocked,
							getAuthorities(loginUser.getRole().getId()));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}
	
	private List<String> getRoles(Integer role) {

		List<String> roles = new ArrayList<String>();

		switch (role.intValue()) {
		case EParkingApplication.USER: 
			roles.add("ROLE_USER");
			break;
		case EParkingApplication.ADMIN: 
			roles.add("ROLE_ADMIN");
			break;
		case EParkingApplication.OWNER:
			roles.add("ROLE_OWNER");
			break;
		case EParkingApplication.SECURITY:
			roles.add("ROLE_SECURITY");
			break;
		}
		return roles;
	}
	
	private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}