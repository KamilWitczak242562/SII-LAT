package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.model.PromoCode;
import com.sii.app.util.PromoCodeCalculator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class DiscountService {
    private final ProductService productService;
    private final PromoCodeService promoCodeService;

    @Autowired
    public DiscountService(ProductService productService, PromoCodeService promoCodeService) {
        this.productService = productService;
        this.promoCodeService = promoCodeService;
    }

    public double calculateNewPrice(Long id, String code) {
        PromoCode promoCode = promoCodeService.getOnePromoCode(code);
        Product product = productService.getOneById(id);
        return PromoCodeCalculator.calculate(product.getPrice(), promoCode.getDiscount(), promoCode.isSecondType());
    }
}
