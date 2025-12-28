package org.example.sbgroup2.repositories;

import org.example.sbgroup2.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {}
