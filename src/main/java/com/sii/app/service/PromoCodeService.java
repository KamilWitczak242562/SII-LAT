package com.sii.app.service;

import com.sii.app.model.PromoCode;
import com.sii.app.repository.PromoCodeRepository;
import com.sii.app.util.PromoCodeGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PromoCodeService implements ApiService<PromoCode> {
    private final PromoCodeRepository promoCodeRepository;

    @Autowired
    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    @Override
    public PromoCode create(PromoCode entity) {
        if (entity == null) throw new InvalidParameterException("Passed parameter is invalid.");
        if (checkIfExist(entity)) throw new EntityExistsException("Promo code already in database.");
        if (entity.getCurrency().isEmpty() || entity.getDiscount() == null || entity.getAllowedUsages() == null)
            throw new InvalidParameterException("At least one of the promo code parameters are invalid.");
        while (promoCodeRepository.findPromoCodeByCode(entity.getCode()).isPresent() || entity.getCode() == null) {
            entity.setCode(PromoCodeGenerator.generate());
        }
        entity.setExpirationDate(LocalDate.now().plusWeeks(6));
        return promoCodeRepository.save(entity);
    }

    @Override
    public List<PromoCode> getAll() {
        return promoCodeRepository.findAll();
    }

    public PromoCode getOnePromoCode(String code) {
        if (code.isEmpty()) throw new InvalidParameterException("Promo code is invalid.");
        return promoCodeRepository.findPromoCodeByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Product with given id was not found in the database."));
    }

    @Override
    public boolean checkIfExist(Long id) {
        return promoCodeRepository.findAll().stream().filter(product -> product.getPromoCodeId().equals(id)).toList().size() == 1;
    }

    @Override
    public boolean checkIfExist(PromoCode entity) {
        Iterable<PromoCode> allPromoCodes = promoCodeRepository.findAll();

        for (PromoCode p : allPromoCodes) {
            if (entity.equals(p)) {
                return true;
            }
        }
        return false;
    }
}
