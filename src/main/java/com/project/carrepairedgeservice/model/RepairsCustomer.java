package com.project.carrepairedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class RepairsCustomer {
    private String uuid;
    private String name;
    private String email;
    private String phoneNumber;
    private String licensePlate;
    private String carBrand;
    private String carModel;
    private List<RepairDetailed> repairDetailedList;

    public RepairsCustomer(Customer customer, List<Repair> repairs, List<Employee> employees) {
        setUuid(customer.getUuid());
        setName(customer.getFirstName() + " " + customer.getLastName());
        setEmail(customer.getEmail());
        setPhoneNumber(customer.getPhoneNumber());
        setLicensePlate(customer.getLicensePlate());
        setCarBrand(customer.getCarBrand());
        setCarModel(customer.getCarModel());
        repairDetailedList = new ArrayList<>();
        for (int i = 0; i < repairs.size(); i++) {
            repairDetailedList.add(new RepairDetailed(
                    customer.getUuid(),
                    customer.getFirstName() + " " + customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getLicensePlate(),
                    customer.getCarBrand(),
                    customer.getCarModel(),
                    employees.get(i).getFirstName() + " " + employees.get(i).getLastName(),
                    repairs.get(i).getType(),
                    repairs.get(i).getDescription(),
                    repairs.get(i).getPrice(),
                    repairs.get(i).getDate(),
                    repairs.get(i).getParts()
                    ));
        }
//        for (int i = 0; i < repairs.size(); i++) {
//            repairDetailedList.add(new RepairDetailed(
//                    employees.get(i).getEmployeeID(),
//                    employees.get(i).getFirstName() + " " + employees.get(i).getLastName(),
//                    employees.get(i).getFunction().getFunctionName(),
//                    repairs.get(i).getType(),
//                    repairs.get(i).getDescription(),
//                    repairs.get(i).getPrice(),
//                    repairs.get(i).getDate(),
//                    repairs.get(i).getParts()
//            ));
//        }

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<RepairDetailed> getRepairDetailedList() {
        return repairDetailedList;
    }

    public void setRepairDetailedList(List<RepairDetailed> repairDetailedList) {
        this.repairDetailedList = repairDetailedList;
    }
}
