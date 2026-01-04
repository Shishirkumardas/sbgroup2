package org.example.sbgroup2.controller;

import lombok.RequiredArgsConstructor;
import org.example.sbgroup2.dto.CustomerFormDTO;
import org.example.sbgroup2.enums.PaymentMethod;
import org.example.sbgroup2.services.MasterDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:3001")
@RequiredArgsConstructor
public class CustomerController {

    private final MasterDataService masterDataService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitCustomerForm(
            @RequestBody CustomerFormDTO dto
    ) {
        return ResponseEntity.ok(masterDataService.saveCustomerForm(dto));
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<String>> getAllPaymentMethods() {
        List<String> methods = PaymentMethod.getAllMethods();
        return ResponseEntity.ok(methods);
    }

}

