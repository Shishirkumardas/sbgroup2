package org.example.sbgroup2.controller;

import org.example.sbgroup2.services.BkashPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/bkash")
public class BkashController {

    @Autowired
    private BkashPaymentService paymentService;

    // This will be used for random payment not for any id, customer,masterData
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam BigDecimal amount) {

        String invoice = "INV-" + System.currentTimeMillis();
        Map<String, Object> response = paymentService.createPayment(amount, invoice);

        return ResponseEntity.ok(response);
    }
    @Autowired
    private BkashPaymentService bkashPaymentService;

    // This will be used for payments for each customer id, masterDataID, cashbackID
    @PostMapping("/pay")
    public ResponseEntity<?> pay(
            @RequestParam Long masterDataId,
            @RequestParam BigDecimal amount) {

        return ResponseEntity.ok(
                bkashPaymentService.makePayment(masterDataId, amount)
        );
    }
}

