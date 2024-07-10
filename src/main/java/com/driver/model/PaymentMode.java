package com.driver.model;

public enum PaymentMode {

    CASH, CARD, UPI;

    public static boolean isValidEnum(String value) {
        for (PaymentMode mode : PaymentMode.values()) {
            if (mode.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}