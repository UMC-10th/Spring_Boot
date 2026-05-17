package com.example.Spring_Boot.domain.store.repository;

import com.example.Spring_Boot.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}