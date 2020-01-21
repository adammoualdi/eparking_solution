package com.parkingapp.server.domain.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class LocationsDistanceDTO {
    
    List<LocationDTO> locations;
    // LocationBookingInfoDTO bookingInfo;
    private LocalDateTime arriveTime;
    private LocalDateTime leavingTime;

    public LocationsDistanceDTO() {
        
    }

    public LocationsDistanceDTO(List<LocationDTO> locations, LocalDateTime arriveTime, LocalDateTime leavingTime) {
        this.locations = locations;
        this.arriveTime = arriveTime;
        this.leavingTime = leavingTime;
    }

    public List<LocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDTO> locations) {
        this.locations = locations;
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

    @Override
    public String toString() {
        return "LocationsDistanceDTO [locations=" + locations + "]";
    }
}
