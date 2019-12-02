package com.parkingapp.server.domain.DTO;

import java.time.LocalDateTime;
// import java.util.Date;

import com.parkingapp.server.domain.Location;
import com.parkingapp.server.domain.UserInfo;

// import javax.persistence.Transient;

/**
 * Saves instances of Roles in the Role entity
 * */

public class BookingDTO {
	private int id;
    private LocationDTO locationId;
    private String username;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;

    public BookingDTO() {

    }

    public BookingDTO(LocationDTO locationId, String username, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        this.locationId = locationId;
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public BookingDTO(int id, LocationDTO locationId, String username, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        this.id = id;
        this.locationId = locationId;
        this.username = username;
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
        return locationId;
    }

    public void setLocationId(LocationDTO locationId) {
        this.locationId = locationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "BookingDTO [active=" + active + ", endDate=" + endDate + ", id=" + id + ", locationId=" + locationId
                + ", startDate=" + startDate + ", userId=" + username + "]";
    }

}
