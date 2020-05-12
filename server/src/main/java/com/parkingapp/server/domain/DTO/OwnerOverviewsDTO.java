package com.parkingapp.server.domain.DTO;

import java.util.ArrayList;

public class OwnerOverviewsDTO {
    
    private ArrayList<OwnerOverviewDTO> ownerOverviewInfo;
    private double percentageBooked;
    private double percentageBookedWeek;

    public OwnerOverviewsDTO() {

    }

    public OwnerOverviewsDTO(ArrayList<OwnerOverviewDTO> ownerOverviewInfo) {
        this.ownerOverviewInfo = ownerOverviewInfo;
    }

    public ArrayList<OwnerOverviewDTO> getOwnerOverviewInfo() {
        return ownerOverviewInfo;
    }

    public void setOwnerOverviewInfo(ArrayList<OwnerOverviewDTO> ownerOverviewInfo) {
        this.ownerOverviewInfo = ownerOverviewInfo;
    }

    public double getPercentageBooked() {
        return percentageBooked;
    }

    public void setPercentageBooked(double percentageBooked) {
        this.percentageBooked = percentageBooked;
    }

    public double getPercentageBookedWeek() {
        return percentageBookedWeek;
    }

    public void setPercentageBookedWeek(double percentageBookedWeek) {
        this.percentageBookedWeek = percentageBookedWeek;
    }

}
