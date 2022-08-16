package com.example.demo.studyroom;

import com.example.demo.studyuser.StudyUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    // 팀플실이름
    private String name;

    // 수용인원
    private Integer capacity;

    // 예약자명
    // 계속 오류가 생겨서 나중에 다시
//    @OneToOne(cascade = CascadeType.ALL)
//    private StudyUser client;
    private String client;
}
