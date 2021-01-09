package com.project.carrepairedgeservice.model;

import java.util.Date;
import java.util.List;

public class Employee {
    private int id;
    private List<WorkingHours> workingHours;
    private Function function;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date startContract;
    private Date endContract;
    private Boolean active;
    private String employeeID;

    public Employee() {

    }

    public Employee(List<WorkingHours> workingHours, Function function, String firstName, String lastName, String phoneNumber, Date startContract, Date endContract, Boolean active) {
        this.workingHours = workingHours;
        this.function = function;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.startContract = startContract;
        this.endContract = endContract;
        this.active = active;
    }

    public Employee(String employeeID, List<WorkingHours> workingHours, Function function, String firstName, String lastName, String phoneNumber, Date startContract, Date endContract, Boolean active) {
        this.employeeID = employeeID;
        this.workingHours = workingHours;
        this.function = function;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.startContract = startContract;
        this.endContract = endContract;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getStartContract() {
        return startContract;
    }

    public void setStartContract(Date startContract) {
        this.startContract = startContract;
    }

    public Date getEndContract() {
        return endContract;
    }

    public void setEndContract(Date endContract) {
        this.endContract = endContract;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}

