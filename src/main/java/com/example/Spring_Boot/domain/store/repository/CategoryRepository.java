package com.example.Spring_Boot.domain.store.repository;

import com.example.Spring_Boot.domain.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            select c
            from Category c
            join fetch c.store s
            where s.storeId in :storeIds
            """)
    List<Category> findCategoriesByStoreIds(@Param("storeIds") List<Long> storeIds);
}
