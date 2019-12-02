package com.parkingapp.server.domain.DTO;

import java.util.List;

public class CarsDTO {
    
    List<CarDTO> cars;

    public CarsDTO() {
        
    }

    public CarsDTO(List<CarDTO> cars) {
        this.cars = cars;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarDTO> cars) {
        this.cars = cars;
    }
    
}