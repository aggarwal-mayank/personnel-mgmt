package com.sapient.personnel.mgmt.controller;

import com.sapient.personnel.mgmt.domain.BusinessUnit;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.domain.SalaryRange;
import com.sapient.personnel.mgmt.domain.Title;
import com.sapient.personnel.mgmt.domain.TotalSalary;
import com.sapient.personnel.mgmt.service.SalaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping(value = "/places/{place}")
    public TotalSalary getTotalSalaryByPlace(@PathVariable Place place) {
        return salaryService.getTotalSalaryBy(place);
    }

    @GetMapping(value = "/BU/{bu}")
    public TotalSalary getTotalSalaryByBU(@PathVariable BusinessUnit bu) {
        return salaryService.getTotalSalaryBy(bu);
    }

    @GetMapping(value = "/supervisors/{id}")
    public TotalSalary getTotalSalaryBySupervisor(@PathVariable Long id) throws EmployeeNotFoundException {
        return salaryService.getTotalSalaryBy(id);
    }

    @GetMapping(value = "/range/title/{title}")
    public SalaryRange getSalaryRangeByTitle(@PathVariable Title title) {
        return salaryService.getSalaryRangeByTitle(title);
    }

}
