// package com.parkingapp.server.domain;

// import javax.persistence.CascadeType;

// // import java.util.Set;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;
// // import javax.persistence.Transient;

// /**
//  * Saves instances of Roles in the Role entity
//  * */
// @Entity
// @Table(name="sensors")
// public class Sensors {
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
// 	private int id;
// 	// @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
// 	@JoinColumn(name="sensor_id")
//     private UserInfo userId;
//     @Column
//     private String regNo;
//     @Column
//     private String model;

//     // @ManyToOne(fetch = FetchType.LAZY)
//     // @JoinColumn(name = "employee_id")
//     // private Employee employee;

//     public Car() {

//     }

//     public Car(UserInfo userId, String regNo, String model) {
//         // this.id = carId;
//         this.userId = userId;
//         this.regNo = regNo;
//         this.model = model;
//     }

//     public Car(int carId, UserInfo userId, String regNo, String model) {
//         this.id = carId;
//         this.userId = userId;
//         this.regNo = regNo;
//         this.model = model;
//     }


//     public int getCarId() {
//         return id;
//     }

//     public void setCarId(int carId) {
//         this.id = carId;
//     }

//     public UserInfo getUserId() {
//         return userId;
//     }

//     public void setUserId(UserInfo userId) {
//         this.userId = userId;
//     }

//     public String getRegNo() {
//         return regNo;
//     }

//     public void setRegNo(String regNo) {
//         this.regNo = regNo;
//     }

//     public String getModel() {
//         return model;
//     }

//     public void setModel(String model) {
//         this.model = model;
//     }

// }
