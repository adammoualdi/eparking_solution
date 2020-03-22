package com.parkingapp.server.domain.DTO;

import java.util.ArrayList;

public class OwnerOverviewsDTO {
    
    private ArrayList<OwnerOverviewDTO> ownerOverviewInfo;

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

}
