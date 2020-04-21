package com.parkingapp.server.domain.DTO;

import com.parkingapp.server.domain.UserInfo;

public class AdminRemoveLocation {

    private int locationId;
    private String message;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
