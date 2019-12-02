package com.parkingapp.server.domain.DTO;

import java.util.List;

public class LocationsDistanceDTO {
    
    List<LocationDTO> locations;

    public LocationsDistanceDTO() {
        
    }

    public LocationsDistanceDTO(List<LocationDTO> locations) {
        this.locations = locations;
    }

    public List<LocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDTO> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "LocationsDistanceDTO [distances=" + locations + "]";
    }
    
}
