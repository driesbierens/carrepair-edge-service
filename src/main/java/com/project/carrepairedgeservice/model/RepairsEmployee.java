package com.project.carrepairedgeservice.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepairsEmployee {

    private String employeeName;
    private String employeePhoneNumber;
    private String employeeId;
    private List<RepairDetailed> repairDetailedList;

    public RepairsEmployee(Employee employee, List<Repair> repairs, List<Customer> customers) {
        setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
        setEmployeeId(employee.getEmployeeID());
        setEmployeePhoneNumber(employee.getPhoneNumber());
        repairDetailedList = new ArrayList<>();
        for (int i = 0; i < repairs.size(); i++) {
            repairDetailedList.add(new RepairDetailed(
                    customers.get(i).getUuid(),
                    customers.get(i).getFirstName() + " " + customers.get(i).getLastName(),
                    customers.get(i).getEmail(),
                    customers.get(i).getPhoneNumber(),
                    customers.get(i).getLicensePlate(),
                    customers.get(i).getCarBrand(),
                    customers.get(i).getCarModel(),
                    employee.getFirstName() + " " + employee.getLastName(),
                    repairs.get(i).getType(),
                    repairs.get(i).getDescription(),
                    repairs.get(i).getPrice(),
                    repairs.get(i).getDate(),
                    repairs.get(i).getParts()
                    ));
        }



    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }


    public List<RepairDetailed> getRepairDetailedList() {
        return repairDetailedList;
    }

    public void setRepairDetailedList(List<RepairDetailed> repairDetailedList) {
        this.repairDetailedList = repairDetailedList;
    }


}
