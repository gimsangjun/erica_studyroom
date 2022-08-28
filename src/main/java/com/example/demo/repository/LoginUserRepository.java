package com.example.demo.repository;

import com.example.demo.dto.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository extends JpaRepository<LoginUser, Integer> {
    Optional<LoginUser> findByloginId(String loginId);
}
