package com.project.carrepairedgeservice.controller;

import com.project.carrepairedgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CarRepairController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${employeeservice.baseurl}")
    private String employeeServiceBaseUrl;

    @Value("${customerservice.baseurl}")
    private String customerServiceBaseUrl;

    @Value("${partservice.baseurl}")
    private String partServiceBaseUrl;

    @Value("${repairservice.baseurl}")
    private String repairServiceBaseUrl;

    @Configuration
    public class RestTemplateClient {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }


//    @GetMapping("/repairs/employee/{employeeId}")
//    public List<RepairsEmployee> getRepairsByEmployeeId(@PathVariable String employeeId) {
//        List<RepairDetail> returnList = new ArrayList<>();
//        //Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, employeeId);
//        ResponseEntity<List<Repair>> responseEntityRepairs =
//                restTemplate.exchange("http://" + "192.168.99.100:31773" + "/repairs/employee/{employeeId}",
//                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repair>>() {
//                        }, employeeId);
//        List<Repair> repairs = responseEntityRepairs.getBody();
//        System.out.println(repairs);
//
//        return (List<RepairDetail>) new RepairDetail();
//    }

    @GetMapping("/repairs/employee/{employeeId}")
    public RepairsEmployee getRepairsByEmployeeId(@PathVariable String employeeId) {

        Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, employeeId);

        ResponseEntity<List<Repair>> responseEntityRepairs =
                restTemplate.exchange("http://" + repairServiceBaseUrl + "/repairs/employee/e001",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repair>>() {});
        List<Repair> repairs = responseEntityRepairs.getBody();
        List<Customer> customers = new ArrayList<>();
        repairs.forEach((customerid) -> {
            Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/c1e6eab90e4c84ef3999558b155c1c727", Customer.class, customerid.getCustomerId());
            customers.add(customer);
        });
        return new RepairsEmployee(employee, responseEntityRepairs.getBody(), customers);

    }

    @GetMapping("/repairs/customer/{customerId}")
    public RepairsCustomer getRepairsByCustomerId(@PathVariable String customerId) {

        Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/{uuid}", Customer.class, customerId);
        ResponseEntity<List<Repair>> responseEntityRepairs =
                restTemplate.exchange("http://" + repairServiceBaseUrl + "/repairs/customer/c001",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repair>>() {});
        List<Repair> repairs = responseEntityRepairs.getBody();
        List<Employee> employees = new ArrayList<>();
        repairs.forEach((employeeid) -> {
            Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/e0d5e82c5529742c48bb00082f45438fe", Employee.class, employeeid.getEmployeeId());
            employees.add(employee);
        });
        return new RepairsCustomer(customer, repairs, employees);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        ResponseEntity<List<Employee>> responseEntityEmployees =
                restTemplate.exchange("http://" + employeeServiceBaseUrl + "/employees",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {});
        List<Employee> employees = responseEntityEmployees.getBody();
        return employees;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        ResponseEntity<List<Customer>> responseEntityCustomers =
                restTemplate.exchange("http://" + customerServiceBaseUrl + "/customers",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {});
        List<Customer> customers = responseEntityCustomers.getBody();
        return customers;
    }






}
