package com.sapient.personnel.mgmt.domain;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends Exception {

    private final HttpStatus httpStatus;

    public EmployeeNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
