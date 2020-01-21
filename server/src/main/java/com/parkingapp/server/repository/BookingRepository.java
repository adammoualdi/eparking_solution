package com.parkingapp.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
		// @Query(
		// 	value = "SELECT * FROM Bookings u WHERE u.status = 1", 
		// 	nativeQuery = true)
		// ArrayList<Booking> findAvailableLocation();
		@Query("SELECT b FROM Booking b WHERE b.locationId = :locationId AND (b.startDate >= :arriveTime AND b.endDate <= :leavingTime) OR (b.startDate <= :arriveTime AND (b.endDate >= :arriveTime AND b.endDate <= :leavingTime)) OR ((b.startDate >= :arriveTime AND b.startDate <= :leavingTime) AND b.endDate >= :leavingTime) OR (b.startDate <= :arriveTime AND b.endDate >= :leavingTime)") 
		ArrayList<Booking> findAvailableLocation(@Param("locationId") Location locationId,
												 @Param("arriveTime") LocalDateTime arriveTime,
												 @Param("leavingTime") LocalDateTime leavingTime);

													// private int id;
													// private LocationDTO locationId;
													// private String username;
													// private LocalDateTime startDate;
													// private LocalDateTime endDate;
													// private boolean active;

		//   Collection<User> findAllActiveUsersNative();)
		// @Query("SELECT t.title FROM Todo t where t.id = :id") 
		// ArrayList<Booking> findByUserId(@Param("id") int id);
	    // Car findByUsernameAndPassword(String username, String password);
//	    ArrayList<UserInfo> findByRoleId(int roleId);
}
