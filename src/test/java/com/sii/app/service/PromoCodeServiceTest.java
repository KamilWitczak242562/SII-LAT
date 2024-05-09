package com.sii.app.service;

import com.sii.app.model.PromoCode;
import com.sii.app.repository.PromoCodeRepository;
import com.sii.app.util.PromoCodeGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromoCodeServiceTest {

    @Mock
    private PromoCodeRepository promoCodeRepository;

    private AutoCloseable autoCloseable;
    private PromoCodeService promoCodeService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        promoCodeService = new PromoCodeService(promoCodeRepository);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createPromoCode_WithValidInput_ReturnsPromoCode() {
        //given
        PromoCode promoCode = new PromoCode(50.99, "PLN", 10);

        //when
        when(promoCodeRepository.save(any(PromoCode.class))).thenReturn(promoCode);
        PromoCode savedPromoCode = promoCodeService.create(promoCode);

        //then
        assertNotNull(savedPromoCode);
        assertEquals("PLN", savedPromoCode.getCurrency());
        assertEquals(50.99, savedPromoCode.getDiscount());
        assertEquals(10, savedPromoCode.getAllowedUsages());

        verify(promoCodeRepository).save(any(PromoCode.class));
    }

    @Test
    void createPromoCode_WithNull_ThrowsException() {
        //given
        PromoCode promoCode = null;

        //when & then
        assertThrows(InvalidParameterException.class, () -> promoCodeService.create(promoCode));
    }

    @Test
    void createPromoCode_ThatAlreadyExist_ThrowsException() {
        //given
        PromoCode promoCode = new PromoCode(50.99, "PLN", 10);
        PromoCode promoCodeNew = new PromoCode(50.99, "PLN", 10);

        //when
        when(promoCodeRepository.findAll()).thenReturn(Collections.singletonList(promoCode));

        //when & then
        assertThrows(EntityExistsException.class, () -> promoCodeService.create(promoCodeNew));
    }

    @Test
    void createPromoCode_WithInvalidDiscount_ThrowsException() {
        //given
        PromoCode promoCode = new PromoCode(null, "PLN", 10);

        //when & then
        assertThrows(InvalidParameterException.class, () -> promoCodeService.create(promoCode));
    }

    @Test
    void createPromoCode_WithInvalidCurrency_ThrowsException() {
        //given
        PromoCode promoCode = new PromoCode(50.99, "", 10);

        //when & then
        assertThrows(InvalidParameterException.class, () -> promoCodeService.create(promoCode));
    }

    @Test
    void createPromoCode_WithInvalidAllowedUsages_ThrowsException() {
        //given
        PromoCode promoCode = new PromoCode(50.99, "PLN", null);

        //when & then
        assertThrows(InvalidParameterException.class, () -> promoCodeService.create(promoCode));
    }

    @Test
    void getAllPromoCodes() {
        //given
        PromoCode promoCode1 = new PromoCode(50.99, "USD", 5);
        PromoCode promoCode2 = new PromoCode(60.99, "PLN", 10);
        List<PromoCode> promoCodes = new ArrayList<>() {{
            add(promoCode1);
            add(promoCode2);
        }};
        //when
        when(promoCodeRepository.findAll()).thenReturn(promoCodes);
        List<PromoCode> foundPromoCodes = promoCodeService.getAll();
        //then
        assertEquals(2, foundPromoCodes.size());
        assertEquals(foundPromoCodes.get(0), promoCodes.get(0));
        assertEquals(foundPromoCodes.get(1), promoCodes.get(1));
    }

    @Test
    void getOnePromoCode_WithValidInput_ReturnsPromoCode() {
        //given
        String code = PromoCodeGenerator.generate();
        PromoCode promoCode1 = new PromoCode(50.99, "USD", 5);
        promoCode1.setCode(code);
        //when
        when(promoCodeRepository.findPromoCodeByCode(code)).thenReturn(Optional.of(promoCode1));
        PromoCode foundPromoCode = promoCodeService.getOnePromoCode(code);
        //then
        assertEquals(foundPromoCode, promoCode1);
    }

    @Test
    void getOnePromoCode_WithEmptyInput_ThrowsException() {
        //given
        String code = "";
        //when & then
        assertThrows(InvalidParameterException.class, () -> promoCodeService.getOnePromoCode(code));
    }

    @Test
    void getOnePromoCode_NotExistingPromoCode_ThrowsException() {
        //given
        String code = PromoCodeGenerator.generate();
        //when
        when(promoCodeRepository.findPromoCodeByCode(code)).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> promoCodeService.getOnePromoCode(code));
    }
}