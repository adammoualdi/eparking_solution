package com.parkingapp.server.domain.DTO;

import java.util.List;

public class DashboardLocationsDTO {
    
    List<DashboardLocationDTO> locations;

    public DashboardLocationsDTO() {
        
    }

    public DashboardLocationsDTO(List<DashboardLocationDTO> locations) {
        this.locations = locations;
    }

    public List<DashboardLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<DashboardLocationDTO> locations) {
        this.locations = locations;
    }
    
}
