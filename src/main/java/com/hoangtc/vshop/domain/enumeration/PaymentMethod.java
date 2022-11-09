package com.hoangtc.vshop.domain.enumeration;

/**
 * The PaymentMethod enumeration.
 */
public enum PaymentMethod {
    CREDIT_CARD("card"),
    IDEAL("ideal");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
