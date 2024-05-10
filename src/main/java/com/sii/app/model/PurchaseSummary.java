package com.sii.app.model;

import java.math.BigDecimal;

public interface PurchaseSummary {
    String getCurrency();
    BigDecimal getTotalAmount();
    BigDecimal getTotalDiscount();
    Long getNoOfPurchases();
}
