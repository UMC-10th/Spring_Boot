package com.example.week6_mission.domain.store.repository;

import com.example.week6_mission.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query("select s from Store s where s.id = :storeId")
	Optional<Store> findStoreById(@Param("storeId") Long storeId);
}
