package com.example.Spring_Boot.domain.user.repository;

import com.example.Spring_Boot.domain.user.entity.User;
import com.example.Spring_Boot.domain.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialTypeAndSocialUid(SocialType socialType, String socialUid);
}