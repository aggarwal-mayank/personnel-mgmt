package com.sapient.personnel.mgmt.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void shouldIncrementByPercentage() {
        assertEquals(BigDecimal.valueOf(110), Utils.incrementByPercentage(BigDecimal.valueOf(100), BigDecimal.TEN));
    }

    @Test
    void percentage() {
        assertEquals(BigDecimal.valueOf(10), Utils.percentage(BigDecimal.valueOf(100), BigDecimal.TEN));
    }
}