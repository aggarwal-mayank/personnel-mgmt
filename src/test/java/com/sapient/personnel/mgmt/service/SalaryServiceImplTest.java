package com.sapient.personnel.mgmt.service;

import com.sapient.personnel.mgmt.domain.BusinessUnit;
import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.domain.SalaryRange;
import com.sapient.personnel.mgmt.domain.Title;
import com.sapient.personnel.mgmt.repo.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalaryServiceImplTest {

    @InjectMocks
    SalaryServiceImpl salaryService;

    @Mock
    EmployeeRepo employeeRepo;

    @Test
    void shouldGetTotalSalaryByPlace() {
        when(employeeRepo.sumSalaryByPlace(Place.BLR)).thenReturn(BigDecimal.valueOf(10000));
        assertEquals(BigDecimal.valueOf(10000), salaryService.getTotalSalaryBy(Place.BLR).getTotalSalary());
    }

    @Test
    void shouldGetTotalSalaryByBU() {
        when(employeeRepo.sumSalaryByBusinessUnit(BusinessUnit.GM)).thenReturn(BigDecimal.valueOf(10000));
        assertEquals(BigDecimal.valueOf(10000), salaryService.getTotalSalaryBy(BusinessUnit.GM).getTotalSalary());
    }

    @Test
    void shouldGetTotalSalaryBySupervisor() throws EmployeeNotFoundException {
        when(employeeRepo.sumSalaryBySupervisor(getEmployee())).thenReturn(BigDecimal.valueOf(10000));
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(getEmployee()));
        assertEquals(BigDecimal.valueOf(10000), salaryService.getTotalSalaryBy(1L).getTotalSalary());
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        assertThrows(EmployeeNotFoundException.class, () -> salaryService.getTotalSalaryBy(1L));
    }

    @Test
    void shouldGetSalaryRangeByTitle() {
        when(employeeRepo.getSalaryRangeByTitle(Title.ASSOCIATE)).thenReturn(getSalaryRange());
        final SalaryRange salaryRangeByTitle = salaryService.getSalaryRangeByTitle(Title.ASSOCIATE);
        assertAll(
                () -> assertNotNull(salaryRangeByTitle),
                () -> assertEquals(BigDecimal.ZERO, salaryRangeByTitle.getMinSalary()),
                () -> assertEquals(BigDecimal.TEN, salaryRangeByTitle.getMaxSalary())
        );
    }

    private Collection<SalaryRange> getSalaryRange() {
        List<SalaryRange> salaryRanges = new ArrayList<>();
        salaryRanges.add(new SalaryRange() {
            @Override
            public BigDecimal getMinSalary() {
                return BigDecimal.ZERO;
            }

            @Override
            public BigDecimal getMaxSalary() {
                return BigDecimal.TEN;
            }
        });
        return salaryRanges;
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        return employee;
    }


}