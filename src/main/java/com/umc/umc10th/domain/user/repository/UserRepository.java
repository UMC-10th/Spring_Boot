package com.umc.umc10th.domain.user.repository;


import com.umc.umc10th.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u FROM User u
            WHERE u.id = :userId
            """)
    Optional<User> findUserInfo(@Param("userId") Long userId);

    Optional<User> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}