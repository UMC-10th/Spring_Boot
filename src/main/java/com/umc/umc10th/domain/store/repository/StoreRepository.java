package com.umc.umc10th.domain.store.repository;

import com.umc.umc10th.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
