package com.sii.app.model;

import com.sii.app.util.PromoCodeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PromoCodeTest {

    @Test
    public void test() {
        PromoCode promoCode = new PromoCode();
        System.out.println(promoCode.getCode());
        System.out.println(PromoCodeGenerator.generate());
    }
}