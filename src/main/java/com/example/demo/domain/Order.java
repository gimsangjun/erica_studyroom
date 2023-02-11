package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

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

    // 이렇게 해달라고 요청
//    {
//        "year" : "2022",
//        "month": "10",
//        "date" : "19",
//        "name": "홍길동",
//        "startTime": 15,
//        "endTime": 17
//    }
    // 예약날짜 ex: 2022-10-19
    private LocalDate date;

    // 예약시간.
    private float startTime;
    private float endTime;

    public void setStudyRoom(StudyRoom room){
        this.studyRoom = room;
    }

}
