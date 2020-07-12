package com.sapient.personnel.mgmt.service;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Utils {

    public final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    public BigDecimal incrementByPercentage(BigDecimal base, BigDecimal pct) {
        return base.add(percentage(base, pct));
    }

    public BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(ONE_HUNDRED);
    }

}
