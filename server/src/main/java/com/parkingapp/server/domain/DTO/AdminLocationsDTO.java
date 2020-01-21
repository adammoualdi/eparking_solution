package com.parkingapp.server.domain.DTO;

import java.util.List;

public class AdminLocationsDTO {
    
    List<AdminLocationDTO> locations;

    public AdminLocationsDTO() {
        
    }

    public AdminLocationsDTO(List<AdminLocationDTO> locations) {
        this.locations = locations;
    }

    public List<AdminLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<AdminLocationDTO> locations) {
        this.locations = locations;
    }
    
}
