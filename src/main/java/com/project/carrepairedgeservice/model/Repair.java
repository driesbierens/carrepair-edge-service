package com.project.carrepairedgeservice.model;

import java.time.LocalDate;

public class Repair {
    private String id;
    private String customerId;
    private String employeeId;
    private String type;
    private Double price;
    private LocalDate date;
    private String description;
    private String[] parts;

    public Repair() {

    }

    public Repair(String customerId, String employeeId, String type, Double price, LocalDate date, String description, String[] parts) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.type = type;
        this.price = price;
        this.date = date;
        this.description = description;
        this.parts = parts;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }
}
