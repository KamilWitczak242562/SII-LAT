package com.sii.app.model;

import com.sii.app.util.PromoCodeGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

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
    private Date expirationDate;
    @Column(nullable = false)
    private double discount;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false)
    private int allowedUsages;

}
