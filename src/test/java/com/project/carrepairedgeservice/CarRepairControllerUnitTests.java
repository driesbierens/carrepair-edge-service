package com.project.carrepairedgeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.carrepairedgeservice.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarRepairControllerUnitTests {

    @Value("${employeeservice.baseurl}")
    private String employeeServiceBaseUrl;

    @Value("${customerservice.baseurl}")
    private String customerServiceBaseUrl;

    @Value("${partservice.baseurl}")
    private String partServiceBaseUrl;

    @Value("${repairservice.baseurl}")
    private String repairServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Customer customer1 = new Customer("c001", "1-XLJ-475", "Nick", "Wouters", "nickwouters@gmail.com", "0479677945", "Toyota", "Corolla");
    private Customer customer2 = new Customer("c002", "1-VKF-467", "Bram", "Hendrickx", "bramh@telenet.be", "0487648266", "Renault", "Clio");
    private Customer customerNoUuid = new Customer("1-VKF-467", "Bram", "Hendrickx", "bramh@telenet.be", "0487648266", "Renault", "Clio");

    private Employee employee1 = createEmployees(1);
    private Employee employee2 = createEmployees(2);
    private Employee employeeNoUuid = createEmployees(3);

    private Category category1 = new Category("Banden", "cat01");
    private Category category2 = new Category("Vloeistoffen", "cat02");
    private Category category3 = new Category("Accessoires", "cat03");

    private Part part1 = new Part("Banden", "Gloednieuwe banden", "1234567890", 250.0, "cat01");
    private Part part2 = new Part("Motorolie", "Nieuwe motorolie", "1234567891", 50.0, "cat02");
    private Part part3 = new Part("Koelvloeistof", "Nieuwe koelvloeistof", "1234567892", 50.0, "cat02");
    private Part part4 = new Part("Ruitenwissers", "Nieuwe ruitenwissers", "1234567893", 120.0, "cat03");

    private Repair repair1 = new Repair(customer1.getUuid(), "e001", "Onderhoud", 250.0, LocalDate.now().toString(), "Groot onderhoud voor Mr. Wouters", new String[]{part2.getEanNumber(), part3.getEanNumber()}, "r001");
    private Repair repair2 = new Repair(customer2.getUuid(), "e001", "Onderhoud", 250.0, LocalDate.of(2020, 12, 13).toString(), "Groot onderhoud voor Mr. Hendrickx", new String[]{part2.getEanNumber(), part3.getEanNumber(), part4.getEanNumber()}, "r002");
    private Repair repair3 = new Repair(customer1.getUuid(), "e002", "Banden", 300.0, LocalDate.of(2020, 12, 13).toString(), "Nieuwe banden voor Mr. Wouters", new String[]{part1.getEanNumber()}, "r003");
    private Repair repairToDelete = new Repair(customer2.getUuid(), "e002", "Reparatie", 500.0, LocalDate.of(2020, 5, 24).toString(), "Reparatie voor Mr. Hendrickx", new String[]{part4.getEanNumber(), part3.getEanNumber()}, "r004");

    public CarRepairControllerUnitTests() throws Exception {

    }

    private Employee createEmployees(int employee) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (employee == 1) {
            Employee employee1 = new Employee(
                    "e001",
                    Arrays.asList(
                            new WorkingHours(
                                    formatter.parse("31-Dec-1998 06:37:50"),
                                    formatter.parse("31-Dec-1998 17:37:50")
                            ),
                            new WorkingHours(
                                    formatter.parse("01-Jan-1999 06:37:50"),
                                    formatter.parse("01-Jan-1999 17:37:50")
                            )),
                    new Function("BAAS"),
                    "Jan",
                    "Vermeulen",
                    "0495898989",
                    formatter.parse("31-Dec-1998 06:37:10"),
                    null,
                    true
            );
            return employee1;
        } else if (employee == 2) {
            Employee employee2 = new Employee(
                    "e002",
                    Arrays.asList(
                            new WorkingHours(
                                    formatter.parse("30-Dec-1998 06:37:50"),
                                    formatter.parse("30-Dec-1998 17:37:50")
                            ),
                            new WorkingHours(
                                    formatter.parse("10-Jan-1999 06:37:50"),
                                    formatter.parse("10-Jan-1999 17:37:50")
                            )),
                    new Function("CEO2.0"),
                    "Wout",
                    "Vermeulen",
                    "021312321",
                    formatter.parse("31-Dec-1998 06:37:10"),
                    null,
                    true
            );
            return employee2;
        } else {
            return new Employee(
                    Arrays.asList(
                            new WorkingHours(
                                    formatter.parse("30-Dec-1998 06:37:50"),
                                    formatter.parse("30-Dec-1998 17:37:50")
                            ),
                            new WorkingHours(
                                    formatter.parse("10-Jan-1999 06:37:50"),
                                    formatter.parse("10-Jan-1999 17:37:50")
                            )),
                    new Function("CEO2.0"),
                    "Jef",
                    "Vermeulen",
                    "021312324",
                    formatter.parse("31-Dec-1998 06:37:10"),
                    null,
                    true
            );

        }
    }

    private List<Repair> allRepairsForCustomer1 = Arrays.asList(repair1, repair3);
    private List<Repair> allRepairsForCustomer2 = Arrays.asList(repair2, repairToDelete);
    private List<Repair> allRepairsForEmployee1 = Arrays.asList(repair1, repair2);
    private List<Repair> allRepairsForEmployee2 = Arrays.asList(repair3, repairToDelete);
    private List<Repair> allRepairsForDate = Arrays.asList(repair2, repair3);

    private List<Customer> allCustomers = Arrays.asList(customer1, customer2);
    private List<Employee> allEmployees = Arrays.asList(employee1, employee2);
    private List<Part> allParts = Arrays.asList(part1, part2, part3, part4);
    private List<Category> allCategories = Arrays.asList(category1, category2, category3);

    @BeforeEach
    public void initializeMockServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetRepairsByEmployeeId_thenReturnRepairDetailsJson() throws Exception {

        // GET all repairs from Employee 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + repairServiceBaseUrl + "/repairs/employee/e001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRepairsForEmployee1))
                );

        // GET Employee 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/e001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee1))
                );

        // Get Customer 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer1))
                );
        // Get Part 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567891")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part2))
                );
        // Get Part 3 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567892")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part3))
                );
        // Get Customer 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c002")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer2))
                );
        // Get Part 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567891")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part2))
                );
        // Get Part 3 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567892")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part3))
                );

        // Get Part 4 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567893")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part4))
                );

        mockMvc.perform(get("/repairs/employee/{employeeId}", "e001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customer.uuid", is("c001")))
                .andExpect(jsonPath("$[0].employee.employeeID", is("e001")))
                .andExpect(jsonPath("$[0].type", is("Onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud voor Mr. Wouters")))
                .andExpect(jsonPath("$[0].parts[0].eanNumber", is("1234567891")))
                .andExpect(jsonPath("$[0].parts[1].eanNumber", is("1234567892")))
                .andExpect(jsonPath("$[0].repairUuid", is("r001")))
                .andExpect(jsonPath("$[1].customer.uuid", is("c002")))
                .andExpect(jsonPath("$[1].employee.employeeID", is("e001")))
                .andExpect(jsonPath("$[1].type", is("Onderhoud")))
                .andExpect(jsonPath("$[1].price", is(250.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.of(2020, 12, 13).toString())))
                .andExpect(jsonPath("$[1].description", is("Groot onderhoud voor Mr. Hendrickx")))
                .andExpect(jsonPath("$[1].parts[0].eanNumber", is("1234567891")))
                .andExpect(jsonPath("$[1].parts[1].eanNumber", is("1234567892")))
                .andExpect(jsonPath("$[1].parts[2].eanNumber", is("1234567893")))
                .andExpect(jsonPath("$[1].repairUuid", is("r002")));
    }

    @Test
    public void whenGetRepairsByCustomerId_thenReturnRepairDetailsJson() throws Exception {
        // GET all repairs from Customer 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + repairServiceBaseUrl + "/repairs/customer/c001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRepairsForCustomer1))
                );

        // Get Customer 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer1))
                );

        // GET Employee 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/e001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee1))
                );

        // Get Part 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567891")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part2))
                );
        // Get Part 3 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567892")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part3))
                );

        // GET Employee 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/e002")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee2))
                );

        // Get Part 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567890")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part1))
                );

        mockMvc.perform(get("/repairs/customer/{customerId}", "c001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customer.uuid", is("c001")))
                .andExpect(jsonPath("$[0].employee.employeeID", is("e001")))
                .andExpect(jsonPath("$[0].type", is("Onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud voor Mr. Wouters")))
                .andExpect(jsonPath("$[0].parts[0].eanNumber", is("1234567891")))
                .andExpect(jsonPath("$[0].parts[1].eanNumber", is("1234567892")))
                .andExpect(jsonPath("$[0].repairUuid", is("r001")))
                .andExpect(jsonPath("$[1].customer.uuid", is("c001")))
                .andExpect(jsonPath("$[1].employee.employeeID", is("e002")))
                .andExpect(jsonPath("$[1].type", is("Banden")))
                .andExpect(jsonPath("$[1].price", is(300.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.of(2020, 12, 13).toString())))
                .andExpect(jsonPath("$[1].description", is("Nieuwe banden voor Mr. Wouters")))
                .andExpect(jsonPath("$[1].parts[0].eanNumber", is("1234567890")))
                .andExpect(jsonPath("$[1].repairUuid", is("r003")));


    }

    @Test
    public void whenGetRepairsByDate_thenReturnRepairDetailsJson() throws Exception {
        // GET all repairs from Date
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + repairServiceBaseUrl + "/repairs/date/" + LocalDate.of(2020, 12, 13).toString())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allRepairsForDate))
                );

        // Get Customer 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c002")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer2))
                );

        // GET Employee 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/e001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee1))
                );

        // Get Part 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567891")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part2))
                );
        // Get Part 3 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567892")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part3))
                );

        // Get Part 4 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567893")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part4))
                );

        // Get Customer 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer1))
                );

        // GET Employee 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/e002")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee2))
                );

        // Get Part 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567890")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part1))
                );

        mockMvc.perform(get("/repairs/date/{date}", (LocalDate.of(2020, 12, 13).toString())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customer.uuid", is("c002")))
                .andExpect(jsonPath("$[0].employee.employeeID", is("e001")))
                .andExpect(jsonPath("$[0].type", is("Onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.of(2020, 12, 13).toString())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud voor Mr. Hendrickx")))
                .andExpect(jsonPath("$[0].parts[0].eanNumber", is("1234567891")))
                .andExpect(jsonPath("$[0].parts[1].eanNumber", is("1234567892")))
                .andExpect(jsonPath("$[0].parts[2].eanNumber", is("1234567893")))
                .andExpect(jsonPath("$[0].repairUuid", is("r002")))
                .andExpect(jsonPath("$[1].customer.uuid", is("c001")))
                .andExpect(jsonPath("$[1].employee.employeeID", is("e002")))
                .andExpect(jsonPath("$[1].type", is("Banden")))
                .andExpect(jsonPath("$[1].price", is(300.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.of(2020, 12, 13).toString())))
                .andExpect(jsonPath("$[1].description", is("Nieuwe banden voor Mr. Wouters")))
                .andExpect(jsonPath("$[1].parts[0].eanNumber", is("1234567890")))
                .andExpect(jsonPath("$[1].repairUuid", is("r003")));
    }

    @Test
    public void whenGetAllParts_thenReturnPartsJson() throws Exception {
        // GET all parts
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/view")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allParts))
                );

        mockMvc.perform(get("/parts"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].eanNumber", is("1234567890")))
                .andExpect(jsonPath("$[1].eanNumber", is("1234567891")))
                .andExpect(jsonPath("$[2].eanNumber", is("1234567892")))
                .andExpect(jsonPath("$[3].eanNumber", is("1234567893")));
    }

    @Test
    public void whenGetAllCategories_thenReturnCategoriesJson() throws Exception {
        // Get all categories
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/categories")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allCategories))
                );

        mockMvc.perform(get("/parts/categories"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].categoryId", is("cat01")))
                .andExpect(jsonPath("$[1].categoryId", is("cat02")))
                .andExpect(jsonPath("$[2].categoryId", is("cat03")));
    }

    @Test
    public void whenGetCategoryById_thenReturnCategoryJson() throws Exception {
        // GET category with id cat01
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/categories/category/cat01")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(category1))
                );

        mockMvc.perform(get("/parts/categories/category/{categoryId}", "cat01"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId", is("cat01")))
                .andExpect(jsonPath("$.name", is("Banden")));
    }

    @Test
    public void whenGetAllEmployees_thenReturnEmployeesJson() throws Exception {
        // Get all employees
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allEmployees))
                );

        mockMvc.perform(get("/employees"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeID", is("e001")))
                .andExpect(jsonPath("$[1].employeeID", is("e002")));
    }

    @Test
    public void whenGetAllCustomers_thenReturnCustomersJson() throws Exception {
        // Get all customers
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customers")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allCustomers))
                );
        mockMvc.perform(get("/customers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].uuid", is("c001")))
                .andExpect(jsonPath("$[1].uuid", is("c002")));
    }

    @Test
    public void whenGetCustomerByUuid_thenReturnCustomerJson() throws Exception {
        // Get customer 1 by id
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer1))
                );

        mockMvc.perform(get("/customers/uuid/{uuid}", "c001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is("c001")))
                .andExpect(jsonPath("$.firstName", is("Nick")))
                .andExpect(jsonPath("$.lastName", is("Wouters")));
    }

    @Test
    public void givenRepair_whenAddRepair_thenReturnRepairDetailJson() throws Exception {
        Repair repair = new Repair(customer1.getUuid(), "e001", "Banden", 500.0, LocalDate.of(2020, 12, 13).toString(), "Nieuwe banden voor Mr. Wouters", new String[]{part1.getEanNumber()});

        // Post Repair for customer 1 by employee 1 with part 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + repairServiceBaseUrl + "/repairs")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(repair))
                );

        // Get Customer 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/c001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer1))
                );

        // GET Employee 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/e001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee1))
                );

        // Get Part 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567890")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part1))
                );

        mockMvc.perform(post("/repairs")
                .content(mapper.writeValueAsString(repair))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.uuid", is("c001")))
                .andExpect(jsonPath("$.employee.employeeID", is("e001")))
                .andExpect(jsonPath("$.type", is("Banden")))
                .andExpect(jsonPath("$.price", is(500.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.of(2020, 12, 13).toString())))
                .andExpect(jsonPath("$.description", is("Nieuwe banden voor Mr. Wouters")))
                .andExpect(jsonPath("$.parts[0].eanNumber", is("1234567890")));
    }

    @Test
    public void givenRepair_whenUpdateRepair_thenReturnRepairDetailJson() throws Exception {
        Repair repair = new Repair(customer1.getUuid(), "e002", "Banden", 300.0, LocalDate.of(2020, 12, 13).toString(), "Nieuwe banden voor Mr. Wouters en motorolie", new String[]{part1.getEanNumber(), part2.getEanNumber()}, "r003");

        // PUT repair for customer 1 with repairid 0003
        mockServer.expect((ExpectedCount.once()),
                requestTo(new URI("http://" + repairServiceBaseUrl + "/repairs")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(repair))
                );

        // GET customer for repair
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + customerServiceBaseUrl + "/customer/uuid/" + repair.getCustomerId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customer1))
                );

        // GET Employee for repair
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + employeeServiceBaseUrl + "/employees/" + repair.getEmployeeId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee2))
                );

        // GET parts for repair
        // Get Part 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567890")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part1))
                );

        // Get Part 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts/part/1234567891")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part2))
                );

        mockMvc.perform(put("/repairs")
                .content(mapper.writeValueAsString(repair))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.uuid", is("c001")))
                .andExpect(jsonPath("$.employee.employeeID", is("e002")))
                .andExpect(jsonPath("$.repairUuid", is("r003")))
                .andExpect(jsonPath("$.parts[0].eanNumber", is("1234567890")))
                .andExpect(jsonPath("$.parts[1].eanNumber", is("1234567891")));
    }

    @Test
    public void givenPart_whenAddPart_thenReturnPartJson() throws Exception {
        Part part = new Part("Spiegel", "Spiegel voor linkerdeur", "1234567894", 150.0, "cat01");

        // POST part
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part))
                );

        mockMvc.perform(post("/parts")
                .content(mapper.writeValueAsString(part))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Spiegel")))
                .andExpect(jsonPath("$.description", is("Spiegel voor linkerdeur")))
                .andExpect(jsonPath("$.eanNumber", is("1234567894")))
                .andExpect(jsonPath("$.price", is(150.0)))
                .andExpect(jsonPath("$.categoryID", is("cat01")));

    }

    @Test
    public void givenPart_whenUpdatePart_thenReturnPartJson() throws Exception {
        Part part = new Part("Ruitenwissers", "Nieuwe ruitenwissers voor Ford", "1234567893", 125.0, "cat02");

        // PUT part with eanNumber 1234567893
        mockServer.expect((ExpectedCount.once()),
                requestTo(new URI("http://" + partServiceBaseUrl + "/parts")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(part))
                );

        mockMvc.perform(put("/parts")
                .content(mapper.writeValueAsString(part))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ruitenwissers")))
                .andExpect(jsonPath("$.description", is("Nieuwe ruitenwissers voor Ford")))
                .andExpect(jsonPath("$.eanNumber", is("1234567893")))
                .andExpect(jsonPath("$.price", is(125.0)))
                .andExpect(jsonPath("$.categoryID", is("cat02")));

    }

    @Test
    public void givenUuid_whenDeleteRepair_thenReturnStatusOk() throws Exception {
        // DELETE repair with uuid r004
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + repairServiceBaseUrl + "/repairs/uuid/r004")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/repairs/uuid/{uuid}", "r004"))
                .andExpect(status().isOk());
    }
}