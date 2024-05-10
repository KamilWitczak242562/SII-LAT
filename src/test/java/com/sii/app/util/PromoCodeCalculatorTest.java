package com.sii.app.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromoCodeCalculatorTest {

    @Test
    public void calculate_WithFirstTypeDiscount_ReturnsCorrectValue() {
        //given
        double price = 100.0;
        double discount = 20.0;
        boolean secondType = false;

        //when
        double result = PromoCodeCalculator.calculate(price, discount, secondType);

        //then
        assertEquals(80.0, result, 0.01);
    }

    @Test
    public void calculate_WithSecondTypeDiscount_ReturnsCorrectValue() {
        //given
        double price = 100.0;
        double discount = 20.0;
        boolean secondType = true;

        //when
        double result = PromoCodeCalculator.calculate(price, discount, secondType);

        //then
        assertEquals(80.0, result, 0.01);
    }

    @Test
    public void calculate_WithNegativeResult_ReturnsZero() {
        //given
        double price = 10.0;
        double discount = 20.0;
        boolean secondType = false;

        //when
        double result = PromoCodeCalculator.calculate(price, discount, secondType);

        //then
        assertEquals(0.0, result, 0.01);
    }

}