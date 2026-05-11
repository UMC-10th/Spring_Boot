package com.example.umc10thtest.domain.store.repository;

import com.example.umc10thtest.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
