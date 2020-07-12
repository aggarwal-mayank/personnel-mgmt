package com.sapient.personnel.mgmt.controller;

import com.sapient.personnel.mgmt.domain.ApiError;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<ApiError> handleUserException(EmployeeNotFoundException ex) {
        final HttpStatus httpStatus = ex.getHttpStatus();
        return ResponseEntity
                .status(httpStatus)
                .body(new ApiError(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage()));
    }

}
