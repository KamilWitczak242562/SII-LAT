package com.sii.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Promo_Code")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_code_id")
    private Long promoCodeId;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    @Column(nullable = false)
    private Double discount;
    @Column(nullable = false)
    private String currency;
    @Column(name = "allowed_usages",nullable = false)
    private Integer allowedUsages;
    @Column(name = "is_second_type", nullable = false)
    private boolean isSecondType;

    public PromoCode(Double discount, String currency, Integer allowedUsages) {
        this.discount = discount;
        this.currency = currency;
        this.allowedUsages = allowedUsages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoCode promoCode = (PromoCode) o;
        return Objects.equals(promoCodeId, promoCode.promoCodeId) && Objects.equals(code, promoCode.code) && Objects.equals(expirationDate, promoCode.expirationDate) && Objects.equals(discount, promoCode.discount) && Objects.equals(currency, promoCode.currency) && Objects.equals(allowedUsages, promoCode.allowedUsages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promoCodeId, code, expirationDate, discount, currency, allowedUsages);
    }
}
