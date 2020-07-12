package com.sapient.personnel.mgmt.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private Long supervisor_id;
    private String title;
    private String bu;
    private String place;
    private BigDecimal salary;
    private String competency;
}
