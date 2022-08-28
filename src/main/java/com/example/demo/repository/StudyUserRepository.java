package com.example.demo.repository;

import com.example.demo.dto.StudyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyUserRepository extends JpaRepository<StudyUser, Integer> {
}
