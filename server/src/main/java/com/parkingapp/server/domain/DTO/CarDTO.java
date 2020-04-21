package com.parkingapp.server.domain.DTO;

// import javax.persistence.Transient;

/**
 * Saves instances of Roles in the Role entity
 * */

public class CarDTO {
    private int id;
    private String regNo;
    private String model;
    private boolean remove;
    private boolean add;

    public CarDTO() {

    }

    public CarDTO(int id, String regNo, String model) {
        this.regNo = regNo;
        this.model = model;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

}
