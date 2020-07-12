package com.sapient.personnel.mgmt.repo;

import com.sapient.personnel.mgmt.domain.BusinessUnit;
import com.sapient.personnel.mgmt.domain.Employee;
import com.sapient.personnel.mgmt.domain.Place;
import com.sapient.personnel.mgmt.domain.SalaryRange;
import com.sapient.personnel.mgmt.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findAllByPlace(Place place);

    @Query("select SUM(e.salary) from Employee e where e.place=:place")
    BigDecimal sumSalaryByPlace(@Param("place")Place place);

    @Query("select SUM(e.salary) from Employee e where e.businessUnit=:bu")
    BigDecimal sumSalaryByBusinessUnit(@Param("bu")BusinessUnit bu);

    @Query("select SUM(e.salary) from Employee e where e.supervisor=:sup")
    BigDecimal sumSalaryBySupervisor(@Param("sup")Employee sup);

    @Query("select MIN(e.salary) as minSalary, MAX(e.salary) as maxSalary from Employee e where e.title=:title")
    Collection<SalaryRange> getSalaryRangeByTitle(@Param("title") Title title);

    @Query(value = "WITH RECURSIVE T(EMPLOYEE_ID) AS\n" +
            "(\n" +
            "    SELECT * FROM EMPLOYEE WHERE SUPERVISOR_ID = ?1\n" +
            "    UNION ALL\n" +
            "    SELECT EMPLOYEE.* FROM EMPLOYEE  JOIN T  ON EMPLOYEE.SUPERVISOR_ID = T.EMPLOYEE_ID\n" +
            ")\n" +
            "SELECT * FROM T", nativeQuery = true)
    List<Employee> findAllSuperviseesOfSupervisor(Long supervisorId);


}
