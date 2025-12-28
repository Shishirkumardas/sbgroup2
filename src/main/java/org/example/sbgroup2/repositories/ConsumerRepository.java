package org.example.sbgroup2.repositories;

import org.example.sbgroup2.models.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    Consumer findByName(String name);

}
