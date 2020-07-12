package com.sapient.personnel.mgmt.service;

import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.repo.EmployeeRepo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findById(Long id) throws EmployeeNotFoundException {
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Unable to find supervisor with Id: "+id, HttpStatus.NOT_FOUND));
    }

    @Override
    @Cacheable(value = "employeeList", key = "#place")
    public List<Employee> findAllByPlace(Place place) {
        return employeeRepo.findAllByPlace(place);
    }

    @Override
    public List<Employee> updateSalaryByPercentageForPlace(Place place, BigDecimal percent) {
        return employeeRepo.findAllByPlace(place).stream()
                .parallel()
                .map(employee -> updateSalary(employee, percent))
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(value = "employeeList", key = "#place")
    public List<Employee> saveAll(List<Employee> employees, Place place) {
        employeeRepo.saveAll(employees);
        return employees;
    }

    @Override
    public List<Employee> findAllSuperviseesOfSupervisor(Long supervisorId) throws EmployeeNotFoundException {
        return employeeRepo.findAllSuperviseesOfSupervisor(findById(supervisorId).getId());

    }

    public Employee updateSalary(Employee employee, BigDecimal percent) {
        employee.setSalary(Utils.incrementByPercentage(employee.getSalary(), percent));
        return employee;
    }
}
