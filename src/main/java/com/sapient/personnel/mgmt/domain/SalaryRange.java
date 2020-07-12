package com.sapient.personnel.mgmt.domain;

import java.math.BigDecimal;

public interface SalaryRange {

    BigDecimal getMinSalary();
    BigDecimal getMaxSalary();
}
