package com.sii.app.repository;

import com.sii.app.model.Purchase;
import com.sii.app.model.PurchaseSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = "SELECT p.currency, " +
            "CAST(SUM(pu.regular_price) AS DECIMAL(10,2)) AS \"TotalAmount\", " +
            "CAST(COALESCE(SUM(pu.discount), 0) AS DECIMAL(10,2)) AS \"TotalDiscount\", " +
            "COUNT(*) AS \"NoOfPurchases\" " +
            "FROM Purchase pu " +
            "JOIN Product p ON pu.product_id = p.product_id " +
            "GROUP BY p.currency",
            nativeQuery = true)
    List<PurchaseSummary> getPurchaseSummary();
}
