package com.parkingapp.server.domain.DTO;

import java.time.LocalDateTime;
// import java.util.Date;

// import javax.persistence.Transient;

import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;

/**
 * Saves instances of Roles in the Role entity
 * */

public class UserBookingDTO {
    
    private int id;
    private LocationDTO location;
    private int userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;

    public UserBookingDTO() {

    }

    // public UserBookingDTO(Location locationId, UserInfo userId, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
    //     this.locationId = locationId;
    //     this.userId = userId;
    //     this.startDate = startDate;
    //     this.endDate = endDate;
    //     this.active = active;
    // }

    public UserBookingDTO(int id, LocationDTO locationId, int userId, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        this.id = id;
        this.location = locationId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocationDTO getLocationId() {
        return location;
    }

    public void setLocationId(LocationDTO locationId) {
        this.location = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserBookingDTO [active=" + active + ", endDate=" + endDate + ", id=" + id + ", startDate=" + startDate
                + "]";
    }

}
