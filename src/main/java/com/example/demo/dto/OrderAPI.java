package com.example.demo.dto;

import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class OrderAPI {

    private String user;
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

    private int year ;
    private int month;
    private int date;
    // 예약시간.
    private int startTime;
    private int endTime;

}
