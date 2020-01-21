package com.parkingapp.server.domain.DTO;

import java.time.LocalDateTime;

public class LocationBookingInfoDTO {
    private LocalDateTime arriveTime;
    private LocalDateTime leavingTime;
    
    public LocationBookingInfoDTO() {

    }

    public LocationBookingInfoDTO(LocalDateTime arriveTime, LocalDateTime leavingTime) {
        this.arriveTime = arriveTime;
        this.leavingTime = leavingTime;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public LocalDateTime getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(LocalDateTime leavingTime) {
        this.leavingTime = leavingTime;
    }
	
}
