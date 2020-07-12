package com.sapient.personnel.mgmt.service;

import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.repo.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepo employeeRepo;

    @Test
    void shouldFindAllEmployeesByPlace() {
        when(employeeRepo.findAllByPlace(Place.BLR)).thenReturn(getEmployees());
        assertNotNull(employeeService.findAllByPlace(Place.BLR));
    }

    @Test
    void shouldUpdateSalaryByPercentageForPlace() {
        when(employeeRepo.findAllByPlace(Place.BLR)).thenReturn(getEmployees());
        final List<Employee> employees = employeeService.updateSalaryByPercentageForPlace(Place.BLR, BigDecimal.TEN);
        assertAll(
                () -> assertNotNull(employees),
                () -> assertEquals(2, employees.size()),
                () -> assertEquals(BigDecimal.valueOf(110L), employees.get(0).getSalary()),
                () -> assertEquals(BigDecimal.valueOf(220L), employees.get(1).getSalary())
        );
    }

    @Test
    void shouldFindAllSuperviseesOfSupervisor() throws EmployeeNotFoundException {
        when(employeeRepo.findAllSuperviseesOfSupervisor(3L)).thenReturn(getEmployees());
        when(employeeRepo.findById(3L)).thenReturn(Optional.of(getEmployee(3L, BigDecimal.valueOf(300L), Place.BLR)));
        final List<Employee> employees = employeeService.findAllSuperviseesOfSupervisor(3L);
        assertAll(
                () -> assertNotNull(employees),
                () -> assertEquals(2, employees.size()),
                () -> assertEquals(BigDecimal.valueOf(100L), employees.get(0).getSalary()),
                () -> assertEquals(BigDecimal.valueOf(200L), employees.get(1).getSalary())
        );
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findAllSuperviseesOfSupervisor(3L));
    }

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(getEmployee(1L, BigDecimal.valueOf(100L), Place.BLR));
        employees.add(getEmployee(2L, BigDecimal.valueOf(200L), Place.BLR));
        return employees;
    }

    private Employee getEmployee(Long id, BigDecimal salary, Place place) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setSalary(salary);
        employee.setPlace(place);
        return employee;
    }
}