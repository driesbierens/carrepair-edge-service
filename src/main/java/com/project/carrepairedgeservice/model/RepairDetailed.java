package com.project.carrepairedgeservice.model;

import java.time.LocalDate;
import java.util.List;

public class RepairDetailed {
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String licensePlate;
    private String carBrand;
    private String carModel;
    private String employeeId;
    private String employeeName;
    private String employeeFunction;
    private String type;
    private String description;
    private Double price;
    private LocalDate date;
    private String[] parts;

//    public RepairDetailed(String employeeId, String employeeName, String employeeFunction, String type, String description, Double price, LocalDate date, String[] parts) {
//        this.employeeId = employeeId;
//        this.employeeName = employeeName;
//        this.employeeFunction = employeeFunction;
//        this.type = type;
//        this.description = description;
//        this.price = price;
//        this.date = date;
//        this.parts = parts;
//    }
    public RepairDetailed(String customerId, String customerName, String customerEmail, String customerPhoneNumber, String licensePlate, String carBrand, String carModel, String employeeName, String type, String description, Double price, LocalDate date, String[] parts) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.licensePlate = licensePlate;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.employeeName = employeeName;
        this.type = type;
        this.description = description;
        this.price = price;
        this.date = date;
        this.parts = parts;
    }



    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
