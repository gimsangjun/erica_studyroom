package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // TODO: 쿼리문이 어떻게 나가는지 체크
    List<Order> findByStudyRoomAndDate(StudyRoom studyRoom, LocalDate date);
    List<Order> findByStudyRoom(StudyRoom studyRoom);

}
