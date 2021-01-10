# Car Repair Service
The Car Repair API enables users and applications to easily keep track of car reparations. An employee will work on a customer's car while using a part of a certain category. An employee has a function/position within the company. Their working hours will also be logged.

#### Architecture
![architecture](./assets/architecture.png)

#### Team Members
[Dries Bierens](https://github.com/driesbierens),
[Aiman Abdulsalam](https://github.com/r0746124),
[Wout Renkin](https://github.com/Wout-Renkin),
[Seppe Alaerts](https://github.com/alaertsseppe)

#### Microservices
Repairs:    https://github.com/driesbierens/repairs-service

Parts:      https://github.com/r0746124/car-part-service

Customers:  https://github.com/alaertsseppe/customer-microservice

Employees:  https://github.com/Wout-Renkin/employee-microservice

#### Frontend Angular Application
https://github.com/r0746124/auto-service

#### Swagger calls
##### GET all Customers (/customers)
![getAllCustomers](./assets/GETallCustomers.PNG)
##### GET Customer by Uuid (/customers/uuid/{uuid})
![getCustomerByUuid](./assets/GETcustomerByUuid.PNG)
##### GET all Employees (/employees)
![getAllEmployees](./assets/GETallEmployees.PNG)
##### GET all Parts (/parts)
![getAllParts](./assets/getAllParts.PNG)
##### POST part (/parts)
![addPart](./assets/AddPart.PNG)
##### PUT part (/parts)
![updatePart](./assets/UpdatePart.PNG)
##### GET all Categories (/parts/categories)
![getAllCategories](./assets/getAllCategories.PNG)
##### GET Category by Id (/parts/categories/category/{categoryId})
![getCategoryById](./assets/getCategoryById.PNG)
##### POST Repair (/repairs)
![addRepair](./assets/AddPart.PNG)
##### PUT Repair (/repairs)
![updateRepair](./assets/UpdatePart.PNG)
##### GET Repairs by CustomerId (/repairs/customer/{customerId})
![getRepairsByCustomerId](./assets/GETRepairsByCustomerId.PNG)
##### GET Repairs by Date (/repairs/date/{date})
![getRepairsByDate](./assets/GETRepairsByDate.PNG)
##### GET Repairs by EmployeeId (/repairs/employee/{employeeId})
![getRepairsByEmployeeId](./assets/GETRepairsByEmployeeId.PNG)
##### DELETE Repair by Uuid (/repairs/uuid/{uuid})
![deleteRepair](./assets/DELETERepairByUuid.PNG)