package com.sapient.personnel.mgmt.domain;

import lombok.Value;

@Value
public class ApiError {

    private Integer status;

    private String error;

    private String message;
}
