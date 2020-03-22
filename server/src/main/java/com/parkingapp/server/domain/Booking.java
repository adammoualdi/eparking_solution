package com.parkingapp.server.domain;

import java.time.LocalDateTime;
// import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
// import javax.persistence.Transient;

/**
 * Saves instances of Roles in the Role entity
 * */
@Entity
@Table(name="bookings")
public class Booking implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="locationId")
    private Location locationId;
    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    private UserInfo userId;
    @Column(name="start_date")
    private LocalDateTime startDate;
    @Column(name="end_date")
    private LocalDateTime endDate;
    @Column(name="active")
    private boolean active;
    @Column(name="parkingId")
    private int parkingSlotId;
    @Column(name="confirmation")
    private boolean parkingConfirmation;
    @Column(name="booking_url")
    private String bookingUrl;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    @Column
    private boolean cancelled;
    @Column
    private double fee;

    public Booking() {

    }

    public Booking(Location locationId, UserInfo userId, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        this.locationId = locationId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public Booking(int id, Location locationId, UserInfo userId, LocalDateTime startDate, LocalDateTime endDate, boolean active) {
        this.id = id;
        this.locationId = locationId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public boolean isParkingConfirmation() {
        return parkingConfirmation;
    }

    public void setParkingConfirmation(boolean parkingConfirmation) {
        this.parkingConfirmation = parkingConfirmation;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

	@Override
	public String toString() {
		return "Booking [active=" + active + ", endDate=" + endDate + ", id=" + id + ", locationId=" + locationId
				+ ", startDate=" + startDate + ", userId=" + userId + "]";
	}

    @Override
    public int compareTo(Object booking) {
        int compareParkingId=((Booking)booking).getParkingSlotId();
        return this.parkingSlotId-compareParkingId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
