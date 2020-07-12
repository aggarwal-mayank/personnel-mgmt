package com.sapient.personnel.mgmt.controller;

import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.findById(id);
    }

    @GetMapping(value = "/places/{place}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployeesByPlace(@PathVariable Place place) {
        return employeeService.findAllByPlace(place);
    }

    @PutMapping(value = "/places/{place}/salaries/percentage/{percent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> updateSalaryByPercentageForPlace(@PathVariable Place place, @PathVariable BigDecimal percent) {
        final List<Employee> employees = employeeService.updateSalaryByPercentageForPlace(place, percent);
        employeeService.saveAll(employees, place);
        return employees;
    }

    @GetMapping(value = "/supervisees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllSupervisees(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.findAllSuperviseesOfSupervisor(id);
    }
}
