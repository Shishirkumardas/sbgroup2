package org.example.sbgroup2.controller;

import lombok.RequiredArgsConstructor;
import org.example.sbgroup2.dto.PurchaseView;
import org.example.sbgroup2.models.Purchase;
import org.example.sbgroup2.repositories.MasterDataRepository;
import org.example.sbgroup2.repositories.PurchaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3001")
public class PurchaseController {

    private final PurchaseRepository repo;
    private final MasterDataRepository masterDataRepository;

//    @GetMapping
//    public List<Purchase> getAll() {
//        return repo.findAll();
//    }
//
//    @PostMapping
//    public Purchase create(@RequestBody Purchase purchase) {
//        return repo.save(purchase);
//    }

    @GetMapping("/purchases")
    public List<PurchaseView> purchases() {
        return masterDataRepository.getPurchases();
    }

}

