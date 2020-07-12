package com.sapient.personnel.mgmt.service;

import com.sapient.personnel.mgmt.domain.BusinessUnit;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.domain.SalaryRange;
import com.sapient.personnel.mgmt.domain.Title;
import com.sapient.personnel.mgmt.domain.TotalSalary;

import java.util.Collection;

public interface SalaryService {

    TotalSalary getTotalSalaryBy(Place place);

    TotalSalary getTotalSalaryBy(BusinessUnit businessUnit);

    TotalSalary getTotalSalaryBy(Long supervisorId) throws EmployeeNotFoundException;

    SalaryRange getSalaryRangeByTitle(Title title);


}
