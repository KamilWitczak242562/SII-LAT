package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.model.PromoCode;
import com.sii.app.model.Purchase;
import com.sii.app.repository.PromoCodeRepository;
import com.sii.app.repository.PurchaseRepository;
import com.sii.app.util.PromoCodeGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {
    @Mock
    private PromoCodeRepository promoCodeRepository;
    @Mock
    private PurchaseRepository purchaseRepository;

    private AutoCloseable autoCloseable;
    private PromoCodeService promoCodeService;

    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        promoCodeService = new PromoCodeService(promoCodeRepository);
        purchaseService = new PurchaseService(purchaseRepository , promoCodeService);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createPurchase_WithProduct_ReturnsPurchase() {
        // Given
        Product product = new Product("Car", "Very good car", 4999.99, "PLN");
        Purchase purchase = new Purchase();
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setProduct(product);
        purchase.setRegularPrice(product.getPrice());
        // When
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        Purchase newPurchase = purchaseService.create(product);

        // Then
        assertNotNull(newPurchase);
        assertEquals(product, newPurchase.getProduct());
        assertEquals(product.getPrice(), newPurchase.getRegularPrice());
        assertNotNull(newPurchase.getDateOfPurchase());
    }

    @Test
    void createPurchase_WithProductAndPromoCodeFirstType_ReturnsPurchase() {
        // Given
        Product product = new Product("Car", "Very good car", 4999.99, "PLN");
        PromoCode promoCode = new PromoCode(50.99, "PLN", 10, false);
        PromoCode promoCodeAfter = new PromoCode(50.99, "PLN", 9, false);
        long id = 1;
        promoCode.setPromoCodeId(id);
        String code = PromoCodeGenerator.generate();
        promoCode.setCode(code);
        Purchase purchase = new Purchase();
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setProduct(product);
        purchase.setRegularPrice(product.getPrice());
        purchase.setDiscount(promoCode.getDiscount());
        // When
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        when(promoCodeRepository.findById(id)).thenReturn(Optional.of(promoCode));
        when(promoCodeRepository.save(promoCode)).thenReturn(promoCodeAfter);
        Purchase newPurchase = purchaseService.create(product, promoCode);

        // Then
        assertNotNull(newPurchase);
        assertEquals(product, newPurchase.getProduct());
        assertEquals(product.getPrice(), newPurchase.getRegularPrice());
        assertNotNull(newPurchase.getDateOfPurchase());
        assertEquals(promoCodeAfter.getDiscount(), newPurchase.getDiscount());
    }

    @Test
    void createPurchase_WithProductAndPromoCodeSecondType_ReturnsPurchase() {
        // Given
        Product product = new Product("Car", "Very good car", 4999.99, "PLN");
        PromoCode promoCode = new PromoCode(50.99, "PLN", 10, true);
        PromoCode promoCodeAfter = new PromoCode(50.99, "PLN", 9, true);
        long id = 1;
        promoCode.setPromoCodeId(id);
        String code = PromoCodeGenerator.generate();
        promoCode.setCode(code);
        Purchase purchase = new Purchase();
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setProduct(product);
        purchase.setRegularPrice(product.getPrice());
        purchase.setDiscount(product.getPrice() * promoCode.getDiscount()/100);
        // When
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        when(promoCodeRepository.findById(id)).thenReturn(Optional.of(promoCode));
        when(promoCodeRepository.save(promoCode)).thenReturn(promoCodeAfter);
        Purchase newPurchase = purchaseService.create(product, promoCode);

        // Then
        assertNotNull(newPurchase);
        assertEquals(product, newPurchase.getProduct());
        assertEquals(product.getPrice(), newPurchase.getRegularPrice());
        assertNotNull(newPurchase.getDateOfPurchase());
        assertEquals(product.getPrice() * promoCodeAfter.getDiscount()/100, newPurchase.getDiscount());
    }


    @Test
    void getAll_ReturnsListOfPurchases() {
        // Given
        Purchase purchase = new Purchase();
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setProduct(new Product("Car", "Very good car", 4999.99, "PLN"));
        purchase.setRegularPrice(100.0);
        List<Purchase> purchases = Collections.singletonList(purchase);

        // When
        when(purchaseRepository.findAll()).thenReturn(purchases);
        List<Purchase> retrievedPurchases = purchaseService.getAll();

        // Then
        assertNotNull(retrievedPurchases);
        assertEquals(purchases.size(), retrievedPurchases.size());
        assertEquals(purchases.get(0), retrievedPurchases.get(0));
    }

}