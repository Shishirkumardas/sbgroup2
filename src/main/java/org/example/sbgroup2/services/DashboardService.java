package org.example.sbgroup2.services;

import lombok.RequiredArgsConstructor;
import org.example.sbgroup2.models.Payment;
import org.example.sbgroup2.models.Purchase;
import org.example.sbgroup2.repositories.ConsumerRepository;
import org.example.sbgroup2.repositories.PaymentRepository;
import org.example.sbgroup2.repositories.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PurchaseRepository purchaseRepo;
    private final PaymentRepository paymentRepo;
    private final ConsumerRepository consumerRepo;

    public DashboardSummaryDTO getSummary() {
        BigDecimal totalPurchase = purchaseRepo.findAll()
                .stream().map(Purchase::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = paymentRepo.findAll()
                .stream().map(Payment::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDue = totalPurchase.subtract(totalPaid);

        double paidPercent = totalPurchase.compareTo(BigDecimal.ZERO) > 0
                ? totalPaid.multiply(BigDecimal.valueOf(100))
                .divide(totalPurchase, 2, RoundingMode.HALF_UP).doubleValue()
                : 0;

        return new DashboardSummaryDTO(
                totalPurchase, totalPaid, totalDue, paidPercent
        );
    }
}

