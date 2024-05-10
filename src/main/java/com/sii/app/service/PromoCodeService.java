package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.model.PromoCode;
import com.sii.app.repository.PromoCodeRepository;
import com.sii.app.util.PromoCodeCalculator;
import com.sii.app.util.PromoCodeGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public PromoCode update(PromoCode entity, Long id) {
        if (entity == null) throw new InvalidParameterException("Passed promo code is invalid");
        if (id <= 0) throw new InvalidParameterException("Passed id is invalid.");
        if (entity.getCurrency().isEmpty() || Double.isNaN(entity.getDiscount()) || entity.getAllowedUsages() == null)
            throw new InvalidParameterException("At least one of the Product parameters are invalid");
        PromoCode promoCodeToChange = promoCodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promo code with given id was not found in the database."));
        promoCodeToChange.setCurrency(entity.getCurrency());
        promoCodeToChange.setDiscount(entity.getDiscount());
        promoCodeToChange.setAllowedUsages(entity.getAllowedUsages());
        return promoCodeRepository.save(promoCodeToChange);
    }

    public PromoCode getOnePromoCode(String code) {
        if (code.isEmpty()) throw new InvalidParameterException("Promo code is invalid.");
        return promoCodeRepository.findPromoCodeByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Promo code was not found in the database."));
    }

    public boolean isExpired(PromoCode promoCode) {
        return promoCode.getExpirationDate().isBefore(LocalDate.now());
    }

    public boolean numberOfUsagesIsAchieved(PromoCode promoCode) {
        return promoCode.getAllowedUsages() == 0;
    }

    public boolean isCurrencyTheSame(Product product, PromoCode promoCode) {
        return product.getCurrency().equals(promoCode.getCurrency());
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
