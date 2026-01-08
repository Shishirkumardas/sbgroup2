package org.example.sbgroup2.services;

import org.example.sbgroup2.ResourceNotFoundException;
import org.example.sbgroup2.enums.OrderStatus;
import org.example.sbgroup2.enums.PaymentMethod;
import org.example.sbgroup2.enums.PaymentStatus;
import org.example.sbgroup2.models.MasterData;
import org.example.sbgroup2.models.Payment;
import org.example.sbgroup2.repositories.PaymentRepository;
import org.example.sbgroup2.repositories.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AreaService areaService;
    @Autowired
    private PaymentRequestRepository paymentRequestRepository;
    @Autowired
    private MasterDataService masterDataService;


    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setPaidAmount(paymentDetails.getPaidAmount());
        return paymentRepository.save(payment);
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    // Master data payment

    public void createPendingPayment(MasterData masterData, BigDecimal amount, String trxId) {

        Payment payment = Payment.builder()
                .masterData(masterData)
                .paidAmount(amount)
                .paymentMethod(PaymentMethod.BKASH)
                .paymentDate(LocalDate.from(LocalDateTime.now()))
                .trxId(trxId)
                .status(PaymentStatus.PAYMENT_CREATED)
                .build();

        paymentRepository.save(payment);
    }

    public void markPaymentSuccess(String trxId) {
        Payment payment = paymentRepository.findByTrxId(trxId);


        payment.setStatus(PaymentStatus.SUCCEEDED);
        paymentRepository.save(payment);

        MasterData md = payment.getMasterData();

        // 🔥 Update paid & due
        BigDecimal newPaid = md.getPaidAmount().add(payment.getPaidAmount());
        md.setPaidAmount(newPaid);
        md.setDueAmount(md.getPurchaseAmount().subtract(newPaid));

        if (md.getDueAmount().compareTo(BigDecimal.ZERO) <= 0) {
            md.setStatus(OrderStatus.PAID);
        } else {
            md.setStatus(OrderStatus.PARTIALLY_PAID);
        }

        masterDataService.updateMasterData(md.getId(), md);

        areaService.recalculateArea(md.getArea().getId());
    }

    public void markPaymentFailed(String trxId) {
        Payment payment = paymentRepository.findByTrxId(trxId);

        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
    }


    public void deletePayment(Long id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }

}
