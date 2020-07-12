package com.sapient.personnel.mgmt.controller;

import com.sapient.personnel.mgmt.domain.ApiError;
import com.sapient.personnel.mgmt.domain.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAdviceTest {

    @Test
    void shouldHandleUserException() {
        ControllerAdvice controllerAdvice = new ControllerAdvice();
        final ResponseEntity<ApiError> responseEntity = controllerAdvice.handleUserException(new EmployeeNotFoundException("Employee not found", HttpStatus.NOT_FOUND));
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode()),
                () -> assertEquals(404, responseEntity.getBody().getStatus())
        );
    }
}