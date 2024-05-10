package com.sii.app.controller;

import com.sii.app.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/calculate/{id}")
    public ResponseEntity<?> calculateNewPrice(@PathVariable Long id, @RequestBody String code) {
        double discount = discountService.calculateNewPrice(id, code);
        return ResponseEntity.ok().body(Map.of("message", "Discount calculated successfully.",
                        "price after discount", String.valueOf(discount)));
    }
}
