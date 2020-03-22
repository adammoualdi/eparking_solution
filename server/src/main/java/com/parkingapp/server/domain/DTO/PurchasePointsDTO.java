package com.parkingapp.server.domain.DTO;

public class PurchasePointsDTO {

	private double deposit;
	// private List<Booking> bookings;

	public PurchasePointsDTO() {
		
    }

    public PurchasePointsDTO(double deposit) {
        this.deposit = deposit;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}
