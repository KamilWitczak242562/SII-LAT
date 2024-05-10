package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.model.PromoCode;
import com.sii.app.model.Purchase;
import com.sii.app.model.PurchaseSummary;
import com.sii.app.repository.PurchaseRepository;
import com.sii.app.util.PromoCodeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PromoCodeService promoCodeService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, PromoCodeService promoCodeService) {
        this.purchaseRepository = purchaseRepository;
        this.promoCodeService = promoCodeService;
    }

    public Purchase create(Product product) {
        Purchase purchase = new Purchase();
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setProduct(product);
        purchase.setRegularPrice(product.getPrice());
        return purchaseRepository.save(purchase);
    }

    public Purchase create(Product product, PromoCode promoCode) {
        Purchase purchase = new Purchase();
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setProduct(product);
        purchase.setRegularPrice(product.getPrice());
        if (promoCode.isSecondType()) {
            purchase.setDiscount(product.getPrice() * promoCode.getDiscount()/100);
        } else {
            purchase.setDiscount(promoCode.getDiscount());
        }
        promoCode.setAllowedUsages(promoCode.getAllowedUsages() - 1);
        promoCodeService.update(promoCode, promoCode.getPromoCodeId());
        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    public List<PurchaseSummary> getSummary() {
        return purchaseRepository.getPurchaseSummary();
    }

}
