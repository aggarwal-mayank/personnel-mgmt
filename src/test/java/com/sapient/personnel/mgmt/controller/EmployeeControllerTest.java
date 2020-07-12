package com.sapient.personnel.mgmt.controller;

import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.Place;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EmployeeControllerTest {

    @LocalServerPort
    private Integer port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate = null;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setup() {
        baseUrl = baseUrl.concat(":").concat(port.toString()).concat("/employees");
    }

    @Test
    void shouldGetAllEmployees() {
        final Employee[] employees = restTemplate.getForObject(baseUrl, Employee[].class);
        assertAll(
                () -> assertNotNull(employees),
                () -> assertEquals(4, employees.length)
        );
    }

    @Test
    void shouldGetEmployeeById() {
        final Employee employee = restTemplate.getForObject(baseUrl.concat("/{id}"), Employee.class, 1L);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals("Iron Man", employee.getName())
        );
    }

    @Test
    void shouldGetAllEmployeesByPlace() {
        final Employee[] employees = restTemplate.getForObject(baseUrl.concat("/places/BLR"), Employee[].class);
        assertAll(
                () -> assertNotNull(employees),
                () -> assertEquals(2, employees.length)
        );
    }

    @Order(1)
    @Test
    void shouldUpdateSalaryByPercentageForPlace() {
        restTemplate.put(baseUrl.concat("/places/BLR/salaries/percentage/10"), null);
        final Employee[] employees = restTemplate.getForObject(baseUrl.concat("/places/BLR"), Employee[].class);
        assertAll(
                () -> assertNotNull(employees),
                () -> assertEquals(2, employees.length),
                () -> assertEquals(new BigDecimal("11000.00"), employees[0].getSalary()),
                () -> assertEquals(new BigDecimal("5500.00"), employees[1].getSalary())
        );
    }

    @Test
    void shouldGetAllSupervisees() {
        final Employee[] employees = restTemplate.getForObject(baseUrl.concat("/supervisees/2"), Employee[].class);
        assertAll(
                () -> assertNotNull(employees)
        );
    }
}