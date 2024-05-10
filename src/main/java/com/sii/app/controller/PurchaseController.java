package com.sii.app.controller;

import com.sii.app.model.Product;
import com.sii.app.model.PromoCode;
import com.sii.app.model.Purchase;
import com.sii.app.model.PurchaseSummary;
import com.sii.app.service.ProductService;
import com.sii.app.service.PromoCodeService;
import com.sii.app.service.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PromoCodeService promoCodeService;
    private final ProductService productService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, PromoCodeService promoCodeService, ProductService productService) {
        this.purchaseService = purchaseService;
        this.promoCodeService = promoCodeService;
        this.productService = productService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createPurchase(@PathVariable Long id) {
        Purchase purchase = purchaseService.create(productService.getOneById(id));
        return ResponseEntity.ok().body(Map.of("message", "Purchased successfully",
                "response", purchase));
    }

    @PostMapping("/create_with_promo_code/{id}")
    public ResponseEntity<?> createPurchase(@PathVariable Long id, @RequestBody String code) {
        String message = "Purchased successfully with promo code.";
        PromoCode promoCode;
        try {
            promoCode = promoCodeService.getOnePromoCode(code);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promo code not found in database.");
        }
        Product product = productService.getOneById(id);
        Purchase purchase = purchaseService.create(product, promoCode);
        if (promoCodeService.numberOfUsagesIsAchieved(promoCode)) {
            message = "Number of usages was achieved." ;
            purchase.setDiscount(0.0);
        }
        if (promoCodeService.isExpired(promoCode)) {
            message = "Promo code is expired.";
            purchase.setDiscount(0.0);
        }
        if (!promoCodeService.isCurrencyTheSame(product, promoCode)){
            message = "Currencies of product price and promo code are not the same.";
            purchase.setDiscount(0.0);
        }
        return ResponseEntity.ok().body(Map.of("message", message,
                "response", purchase));
    }

    @GetMapping("/getSummary")
    public ResponseEntity<?> getSummary() {
        List<PurchaseSummary> purchaseSummaryList = purchaseService.getSummary();
        return ResponseEntity.ok(purchaseSummaryList);
    }
}
