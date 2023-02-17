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
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    @ToString.Exclude
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
        reservation.put("id",user.getUsername());
        reservation.put("name",user.getNickname());
        reservation.put("date",date);
        reservation.put("startTime",startTime);
        reservation.put("endTime",endTime);
        return reservation;
    }

}
