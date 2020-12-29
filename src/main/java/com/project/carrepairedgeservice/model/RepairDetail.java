package com.project.carrepairedgeservice.model;

import java.time.LocalDate;

public class RepairDetail {

    private String id;
    public Customer customer;
    public Employee employee;
    private String type;
    private Double price;
    private LocalDate date;
    private String description;
    private Part[] parts;

    public RepairDetail() {
    }

    public RepairDetail(Customer customer, Employee employee, String type, Double price, LocalDate date, String description, Part[] parts) {
        this.customer = customer;
        this.employee = employee;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public Part[] getParts() {
        return parts;
    }

    public void setParts(Part[] parts) {
        this.parts = parts;
    }
}