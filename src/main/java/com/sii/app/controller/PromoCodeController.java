package com.sii.app.controller;

import com.sii.app.model.PromoCode;
import com.sii.app.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/promo_code")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;
    @Autowired
    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPromoCode(@RequestBody PromoCode promoCode) {
        PromoCode newPromoCode = promoCodeService.create(promoCode);
        return ResponseEntity.ok().body(Map.of("message", "Promo code created successfully.",
                "response", newPromoCode));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        List<PromoCode> promoCodes = promoCodeService.getAll();
        return ResponseEntity.ok().body(Map.of("message", "Promo codes got successfully.",
                "response", promoCodes));
    }

    @PostMapping
    public ResponseEntity<?> updateProduct(@RequestBody String code) {
        PromoCode promoCode = promoCodeService.getOnePromoCode(code);
        return ResponseEntity.ok().body(Map.of("message", "Promo code found successfully.",
                "response", promoCode));
    }
}
