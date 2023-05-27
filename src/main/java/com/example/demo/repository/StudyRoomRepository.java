package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long>, JpaSpecificationExecutor<StudyRoom> {
    Optional<StudyRoom> findById(Long id);
    @Query("SELECT o From Order o WHERE o.studyRoom = :studyRoom AND o.date = :date AND o.state = 'NORMAL' AND ((:startTime BETWEEN o.startTime AND o.endTime) OR (:endTime BETWEEN o.startTime AND o.endTime))")
    List<Order> findByDateBetweenTime(@Param("studyRoom") StudyRoom studyRoom, @Param("date") LocalDate date, @Param("startTime") double startTime, @Param("endTime") double endTime);

    boolean existsByName(String name);

    @Query("SELECT o From Order o WHERE o.date = :date AND o.state = 'NORMAL' AND ((:startTime BETWEEN o.startTime AND o.endTime) OR (:endTime BETWEEN o.startTime AND o.endTime)) AND o.user = :user")
    List<Order> findByDateBetweenTimeAndUser(@Param("date") LocalDate date, @Param("startTime") Double startTime, @Param("endTime") Double endTime, @Param("user") User user);

}
