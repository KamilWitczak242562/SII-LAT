package com.sii.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchaseId;
    @Column(name = "date_of_purchase", nullable = false)
    private Date dateOfPurchase;
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @OneToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;
    @Column(name = "regular_price", nullable = false)
    private Double regularPrice;
    @Column
    private Double discount;
}
