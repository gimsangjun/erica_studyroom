package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // 주인: 외래키값이 있는곳, 여기에는 꼭 값을 넣어줘야한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    // 예약날짜 ex: 2022-10-19
    private LocalDate date;
    // 예약시간.
    private float startTime;
    private float endTime;
    public void setStudyRoom(StudyRoom room){
        this.studyRoom = room;
    }

    public LinkedHashMap<String, String> getResponse(){
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId",id);
        reservation.put("studyRoomId",studyRoom.getId());
        reservation.put("studyRoomName",studyRoom.getName());
        reservation.put("location",studyRoom.getBuilding() +" "+ studyRoom.getLocation());
        reservation.put("name",user.getName());
        reservation.put("date",date);
        reservation.put("startTime",startTime);
        reservation.put("endTime",endTime);
        return reservation;
    }

    // 연관관계 편의 메서드
    public void changeUser(User user){
        this.user = user;
        user.getOrders().add(this);
    }

    // 연관관계 편의 메서드
    public void changeStudyRoom(StudyRoom studyRoom){
        this.studyRoom = studyRoom;
        studyRoom.getOrders().add(this);
    }

}
