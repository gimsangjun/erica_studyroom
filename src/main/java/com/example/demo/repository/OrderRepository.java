package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o From Order o WHERE o.studyRoom = :studyRoom AND o.date = :date AND o.state != '반납' AND o.state != '취소' ORDER BY o.startTime ASC")
    List<Order> findByStudyRoomAndDate(@Param("studyRoom") StudyRoom studyRoom, @Param("date") LocalDate date);

    @Query("SELECT o From Order o WHERE o.studyRoom = :studyRoom AND o.state != '반납' AND o.state != '취소' ORDER BY o.startTime ASC")
    List<Order> findByStudyRoom(@Param("studyRoom") StudyRoom studyRoom);
    List<Order> findByUser(User user);
    List<Order> findByUserAndDate(User user, LocalDate date);

}
