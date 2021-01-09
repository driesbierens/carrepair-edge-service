package com.project.carrepairedgeservice.model;

import java.time.LocalDate;
import java.util.List;

public class RepairDetail {

    private String repairUuid;
    public Customer customer;
    public Employee employee;
    private String type;
    private Double price;
    private String date;
    private String description;
    private List<Part> parts;

    public RepairDetail(String repairUuid, Customer customer, Employee employee, String type, Double price, String date, String description, List<Part> parts) {
        this.repairUuid = repairUuid;
        this.customer = customer;
        this.employee = employee;
        this.type = type;
        this.price = price;
        this.date = date;
        this.description = description;
        this.parts = parts;
    }

    public String getRepairUuid() {
        return repairUuid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public List<Part> getParts() {
        return parts;
    }
}
