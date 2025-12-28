package org.example.sbgroup2.repositories;

import org.example.sbgroup2.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {

    Area findByName(String name);
}
