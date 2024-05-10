package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.model.PromoCode;
import com.sii.app.repository.ProductRepository;
import com.sii.app.repository.PromoCodeRepository;
import com.sii.app.util.PromoCodeGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {
    @Mock
    private PromoCodeRepository promoCodeRepository;
    @Mock
    private ProductRepository productRepository;

    private AutoCloseable autoCloseable;
    private PromoCodeService promoCodeService;
    private ProductService productService;

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        promoCodeService = new PromoCodeService(promoCodeRepository);
        productService = new ProductService(productRepository);
        discountService = new DiscountService(productService, promoCodeService);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void calculateNewPrice_WithValidInputs_ReturnsCorrectPrice() {
        //given
        Product product = new Product("Car", "Very good car", 4999.99, "PLN");
        PromoCode promoCode = new PromoCode(50.99, "PLN", 10, false);
        long id = 1;
        String code = PromoCodeGenerator.generate();
        promoCode.setCode(code);
        promoCode.setExpirationDate(LocalDate.now().plusDays(1));
        //when
        when(promoCodeRepository.findPromoCodeByCode(code)).thenReturn(Optional.of(promoCode));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        double value = discountService.calculateNewPrice(id, promoCode.getCode());
        //then
        assertEquals(value, 4949.00);
    }

    @Test
    void calculateNewPrice_WithInvalidProductId_ThrowsException(){
        //given
        long invalidId = 999999;
        PromoCode promoCode = new PromoCode(50.99, "PLN", 10, false);
        String code = PromoCodeGenerator.generate();
        promoCode.setCode(code);
        promoCode.setExpirationDate(LocalDate.now().plusDays(1));
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());
        when(promoCodeRepository.findPromoCodeByCode(code)).thenReturn(Optional.of(promoCode));

        //when & then
        assertThrows(EntityNotFoundException.class, () -> discountService.calculateNewPrice(invalidId, code));
    }

    @Test
    void getOnePromoCode_WithEmptyInput_ThrowsException() {
        //given
        String code = "";
        //when & then
        assertThrows(InvalidParameterException.class, () -> discountService.calculateNewPrice(1L, code));
    }

    @Test
    void getOnePromoCode_NotExistingPromoCode_ThrowsException() {
        //given
        String code = PromoCodeGenerator.generate();
        //when
        when(promoCodeRepository.findPromoCodeByCode(code)).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> discountService.calculateNewPrice(1L, code));
    }

}