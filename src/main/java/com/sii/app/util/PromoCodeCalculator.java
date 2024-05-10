package com.sii.app.util;

public class PromoCodeCalculator {
    public static double calculate(double price, double discount, boolean secondType) {
        double result;
        if (secondType) {
            result = price - (price * discount/100);
        } else {
            result = price - discount;
        }
        if (result < 0) {
            return 0.0;
        } else {
            return result;
        }
    }
}
