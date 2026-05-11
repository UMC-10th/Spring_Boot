package com.example.Spring_Boot.domain.user.repository;

import com.example.Spring_Boot.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}