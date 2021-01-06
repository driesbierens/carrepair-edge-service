package com.project.carrepairedgeservice.controller;

import com.project.carrepairedgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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

    @GetMapping("/repairs/employee/{employeeId}")
    public List<RepairDetail> getRepairsByEmployeeId(@PathVariable String employeeId) {
        List<RepairDetail> returnList = new ArrayList();

        ResponseEntity<List<Repair>> responseEntityRepairs =
                restTemplate.exchange("http://" + repairServiceBaseUrl + "/repairs/employee/{employeeId}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repair>>() {}, employeeId);
        List<Repair> repairs = responseEntityRepairs.getBody();

        //Get employee
        Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, employeeId);

        for (Repair repair : repairs) {
            //Get customer
            Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/{uuid}", Customer.class, repair.getCustomerId());
            //Get list of parts for repair
            List<Part> parts = new ArrayList<>();
            for (String ean : repair.getParts()) {
                Part part = restTemplate.getForObject("http://" + partServiceBaseUrl + "/parts/part/{eanNumber}", Part.class, ean);
                parts.add(part);
            }
            //Add repair to list
            returnList.add(new RepairDetail(repair.getId(), customer, employee, repair.getType(), repair.getPrice(), repair.getDate(), repair.getDescription(), parts));
        }

        return returnList;
    }

    @GetMapping("repairs/customer/{customerId}")
    public List<RepairDetail> getRepairsByCustomerId(@PathVariable String customerId) {
        List<RepairDetail> returnList = new ArrayList();

        ResponseEntity<List<Repair>> responseEntityRepairs =
                restTemplate.exchange("http://" + repairServiceBaseUrl + "/repairs/customer/{customerId}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Repair>>(){}, customerId);
        List<Repair> repairs = responseEntityRepairs.getBody();

        //Get customer
        Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/{uuid}", Customer.class, customerId);

        for (Repair repair : repairs) {
            //Get employee for repair
            Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, repair.getEmployeeId());
            //Get list of parts for repair
            List<Part> parts = new ArrayList<>();
            for (String ean : repair.getParts()) {
                Part part = restTemplate.getForObject("http://" + partServiceBaseUrl + "/parts/part/{eanNumber}", Part.class, ean);
                parts.add(part);
            }
            //Add the repair to list
            returnList.add(new RepairDetail(repair.getId(), customer, employee, repair.getType(), repair.getPrice(), repair.getDate(), repair.getDescription(), parts));
        }

        return returnList;
    }

    @GetMapping("repairs/date/{date}")
    public List<RepairDetail> getRepairsByDate(@PathVariable LocalDate date) {
        List<RepairDetail> returnList = new ArrayList();

        ResponseEntity<List<Repair>> responseEntityRepairs =
                restTemplate.exchange("http://" + repairServiceBaseUrl + "/repairs/date/{date}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Repair>>(){}, date.toString());
        List<Repair> repairs = responseEntityRepairs.getBody();

        for (Repair repair : repairs) {
            //Get customer for repair
            Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/{uuid}", Customer.class, repair.getCustomerId());
            //Get employee for repair
            Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, repair.getEmployeeId());
            //Get list of parts for repair
            List<Part> parts = new ArrayList<>();
            for (String ean : repair.getParts()) {
                Part part = restTemplate.getForObject("http://" + partServiceBaseUrl + "/parts/part/{eanNumber}", Part.class, ean);
                parts.add(part);
            }
            //Add the repair to list
            returnList.add(new RepairDetail(repair.getId(), customer, employee, repair.getType(), repair.getPrice(), repair.getDate(), repair.getDescription(), parts));
        }

        return returnList;
    }

    @GetMapping("/parts")
    public List<Part> getAllParts() {
        ResponseEntity<List<Part>> responseEntityParts =
                restTemplate.exchange("http://" + partServiceBaseUrl + "/parts/view",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Part>>() {});
        List<Part> parts = responseEntityParts.getBody();
        return parts;
    }

    @GetMapping("/parts/categories")
    public List<Category> getAllCategories() {
        ResponseEntity<List<Category>> responseEntityCategories =
                restTemplate.exchange("http://" + partServiceBaseUrl + "/categories", HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {
                });
        List<Category> categories = responseEntityCategories.getBody();
        return categories;
    }

    @GetMapping("/parts/categories/category/{categoryId}")
    public Category getCategoryById (@PathVariable int categoryId) {
        return restTemplate.getForObject("http://" + partServiceBaseUrl + "/categories/category/{categoryID}", Category.class, categoryId);
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

    @PostMapping("/repairs")
    public RepairDetail addRepair(@RequestBody Repair repair) {
        Repair repair1 = restTemplate.postForObject("http://" + repairServiceBaseUrl + "/repairs",
                repair, Repair.class);
        assert repair1 != null;
        Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/{uuid}", Customer.class, repair1.getCustomerId());
        Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, repair1.getEmployeeId());
        List<Part> parts = new ArrayList<>();
        for (String ean : repair1.getParts()) {
            Part part = restTemplate.getForObject("http://" + partServiceBaseUrl + "/parts/part/{eanNumber}", Part.class, ean);
            parts.add(part);
        }
        return new RepairDetail(repair.getId(), customer, employee, repair.getType(), repair.getPrice(), repair.getDate(), repair.getDescription(), parts);
    }

    @PutMapping("/repairs")
    public RepairDetail updateRepair(@RequestBody Repair repair) {
        ResponseEntity<Repair> responseEntityRepair = restTemplate.exchange("http://" + repairServiceBaseUrl + "/repairs", HttpMethod.PUT, new HttpEntity<>(repair), Repair.class);
        Repair repair1 = responseEntityRepair.getBody();
        Customer customer = restTemplate.getForObject("http://" + customerServiceBaseUrl + "/customer/uuid/{uuid}", Customer.class, repair1.getCustomerId());
        Employee employee = restTemplate.getForObject("http://" + employeeServiceBaseUrl + "/employees/{employeeId}", Employee.class, repair1.getEmployeeId());
        List<Part> parts = new ArrayList<>();
        for (String ean : repair1.getParts()) {
            Part part = restTemplate.getForObject("http://" + partServiceBaseUrl + "/parts/part/{eanNumber}", Part.class, ean);
            parts.add(part);
        }
        return new RepairDetail(repair.getId(), customer, employee, repair.getType(), repair.getPrice(), repair.getDate(), repair.getDescription(), parts);
    }

    @PostMapping("/parts")
    public Part addPart(@RequestBody Part part) {
        return restTemplate.postForObject("http://" + partServiceBaseUrl + "/parts",
                part, Part.class);
    }

    @PutMapping("/parts")
    public Part updatePart(@RequestBody Part part) {
        ResponseEntity<Part> responseEntityPart = restTemplate.exchange("http://" + partServiceBaseUrl + "/parts", HttpMethod.PUT, new HttpEntity<>(part), Part.class);
        return responseEntityPart.getBody();
    }

    @DeleteMapping("/repairs/customer/{customerId}/date/{date}")
    public ResponseEntity deleteRepair(@PathVariable String customerId, @PathVariable String date) {
        restTemplate.delete("http://" + repairServiceBaseUrl + "/repairs/customer/" + customerId + "/date/" + date);
        return ResponseEntity.ok().build();
    }







}
