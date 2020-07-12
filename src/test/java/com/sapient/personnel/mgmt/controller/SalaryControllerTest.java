package com.sapient.personnel.mgmt.controller;

import com.sapient.personnel.mgmt.domain.TotalSalary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SalaryControllerTest {

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
        baseUrl = baseUrl.concat(":").concat(port.toString()).concat("/salaries");
    }

    @Test
    void shouldGetTotalSalaryByPlace() {
        final TotalSalary totalSalary = restTemplate.getForObject(baseUrl.concat("/places/BLR"), TotalSalary.class);
        assertEquals(new BigDecimal("15000.00"), totalSalary.getTotalSalary());
    }

    @Test
    void shouldGetTotalSalaryByBU() {
        final TotalSalary totalSalary = restTemplate.getForObject(baseUrl.concat("/BU/GM"), TotalSalary.class);
        assertEquals(new BigDecimal("13000.00"), totalSalary.getTotalSalary());
    }

    @Test
    void shouldGetTotalSalaryBySupervisor() {
        final TotalSalary totalSalary = restTemplate.getForObject(baseUrl.concat("/supervisors/1"), TotalSalary.class);
        assertEquals(new BigDecimal("11000.00"), totalSalary.getTotalSalary());
    }

    @Test
    void getSalaryRangeByTitle() {
        String response = restTemplate.getForObject(baseUrl.concat("/range/title/ASSOCIATE"), String.class);
        assertAll(
                () -> assertTrue(response.contains("\"minSalary\":5000.00")),
                () -> assertTrue(response.contains("\"maxSalary\":7000.00"))
        );
    }
}