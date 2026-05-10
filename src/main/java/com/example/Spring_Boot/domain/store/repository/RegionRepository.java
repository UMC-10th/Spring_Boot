package com.example.Spring_Boot.domain.store.repository;

import com.example.Spring_Boot.domain.store.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
