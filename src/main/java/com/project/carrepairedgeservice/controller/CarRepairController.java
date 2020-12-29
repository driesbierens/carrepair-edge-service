package com.project.carrepairedgeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Value("${repairservice.baseurl")
    private String repairServiceBaseUrl;
}
