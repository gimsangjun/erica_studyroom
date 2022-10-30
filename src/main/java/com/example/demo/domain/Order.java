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
    // 나중에 User만들면 다시 수정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
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
    // private LocalDate orderDate;

    private int reserveYear ;
    private int reserveMonth;
    private int reserveDate;

    // 예약시간.
    private int startTime;
    private int endTime;

    public void setStudyRoom(StudyRoom room){
        room.countUp();
        this.studyRoom = room;
    }

    public void setYear(int year){
        this.reserveYear = year;
    }

    public void setMonth(int month){
        this.reserveMonth = month;
    }

    public void setDate(int date){
        this.reserveDate = date;
    }

    public int getYear(){
        return reserveYear;
    }

    public int getMonth(){
        return reserveMonth;
    }

    public int getDate(){
        return reserveDate;
    }

}
