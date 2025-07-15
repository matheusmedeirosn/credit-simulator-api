package com.creditsimulator.domain.enums;

import java.math.BigDecimal;

public enum AgeBracketEnum {

    YOUNG(25, "0.05"),
    ADULT(40, "0.03"),
    MIDDLE_AGED(60, "0.02"),
    SENIOR(Integer.MAX_VALUE, "0.04");

    private final int upperLimit;
    private final BigDecimal rate;

    AgeBracketEnum(int upperLimit, String rate) {
        this.upperLimit = upperLimit;
        this.rate = new BigDecimal(rate);
    }

    public static BigDecimal getRate(int age) {
        for (AgeBracketEnum bracket : values()) {
            if (age <= bracket.upperLimit) {
                return bracket.rate;
            }
        }
        throw new IllegalStateException("No rate defined for age: " + age);
    }

}
