package com.sapient.personnel.mgmt.service;

import com.sapient.personnel.mgmt.domain.BusinessUnit;
import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.domain.SalaryRange;
import com.sapient.personnel.mgmt.domain.Title;
import com.sapient.personnel.mgmt.domain.TotalSalary;
import com.sapient.personnel.mgmt.repo.EmployeeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final EmployeeRepo employeeRepo;

    public SalaryServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public TotalSalary getTotalSalaryBy(Place place) {
        return new TotalSalary(employeeRepo.sumSalaryByPlace(place));
    }

    @Override
    public TotalSalary getTotalSalaryBy(BusinessUnit businessUnit) {
        return new TotalSalary(employeeRepo.sumSalaryByBusinessUnit(businessUnit));
    }

    @Override
    public TotalSalary getTotalSalaryBy(Long supervisorId) throws EmployeeNotFoundException {
        return new TotalSalary(employeeRepo.sumSalaryBySupervisor(findById(supervisorId)));
    }

    @Override
    public SalaryRange getSalaryRangeByTitle(Title title) {
        return employeeRepo.getSalaryRangeByTitle(title).stream().findAny().get();
    }

    private Employee findById(Long id) throws EmployeeNotFoundException {
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Unable to find supervisor with Id: "+id, HttpStatus.NOT_FOUND));
    }

}
