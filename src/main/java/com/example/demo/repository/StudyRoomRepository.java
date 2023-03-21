package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    Optional<StudyRoom> findById(Long id);
    Optional<StudyRoom> findByName(String name);
    ArrayList<StudyRoom> findStudyRoomsByUniversity(String university);
    ArrayList<StudyRoom> findStudyRoomsByBuilding(String building);
    ArrayList<StudyRoom> findStudyRoomsByUniversityAndBuilding(String university, String building);

    @Query("SELECT o From Order o WHERE o.studyRoom = :studyRoom AND o.date = :date AND o.state != '반납' AND o.state != '취소' AND o.startTime BETWEEN :startTime AND :endTime")
    List<Order> findByDateBetweenTime(@Param("studyRoom") StudyRoom studyRoom, @Param("date") LocalDate date, @Param("startTime") double startTime, @Param("endTime") double endTime);
}
