package com.sapient.personnel.mgmt.service;

import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(Long id) throws EmployeeNotFoundException;

    List<Employee> findAllByPlace(Place place);

    List<Employee> saveAll(List<Employee> employees, Place place);

    List<Employee> updateSalaryByPercentageForPlace(Place place, BigDecimal percent);

    List<Employee> findAllSuperviseesOfSupervisor(Long supervisorId) throws EmployeeNotFoundException;
}
