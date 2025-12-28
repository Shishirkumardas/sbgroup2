package org.example.sbgroup2.controller;

import lombok.RequiredArgsConstructor;
import org.example.sbgroup2.dto.PaymentView;
import org.example.sbgroup2.models.Area;
import org.example.sbgroup2.models.Payment;
import org.example.sbgroup2.repositories.MasterDataRepository;
import org.example.sbgroup2.repositories.PaymentRepository;
import org.example.sbgroup2.services.AreaSummaryDTO;
import org.example.sbgroup2.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3001")
public class PaymentController {

    private final PaymentRepository repo;
    private final PaymentService paymentService;
    private final MasterDataRepository masterDataRepository;

//    @GetMapping
//    public List<Payment> getAll() {
//        return repo.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Payment getPaymentById(@PathVariable Long id) {
//        return paymentService.getPaymentById(id);
//    }

    @GetMapping
    public List<PaymentView> payments() {
        return masterDataRepository.getPayments();
    }


    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        return paymentService.updatePayment(id, payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().body("Payment deleted successfully");
    }

}

