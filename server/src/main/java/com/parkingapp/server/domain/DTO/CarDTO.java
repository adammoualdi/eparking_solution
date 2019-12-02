package com.parkingapp.server.domain.DTO;

// import javax.persistence.Transient;

/**
 * Saves instances of Roles in the Role entity
 * */

public class CarDTO {
    private String regNo;
    private String model;

    public CarDTO() {

    }

    public CarDTO(String regNo, String model) {
        this.regNo = regNo;
        this.model = model;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
