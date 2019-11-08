package com.parkingapp.server.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;

// import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
// import javax.persistence.Transient;

/**
 * Saves instances of Roles in the Role entity
 * */
@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	// @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	// @JoinColumn(name="user_id")
    // @Column(name="location_id")
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="location_id")
    private Location locationId;
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserInfo userId;
    @Column(name="start_date")
    private LocalDateTime startDate;
    @Column(name="end_date")
    private LocalDateTime endDate;

    public Booking() {

    }

    public Booking(Location locationId, UserInfo userId, LocalDateTime startDate, LocalDateTime endDate) {
        this.locationId = locationId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Booking(int id, Location locationId, UserInfo userId, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.locationId = locationId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public UserInfo getUserId() {
        return userId;
    }

    public void setUserId(UserInfo userId) {
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
}
