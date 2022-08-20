package com.example.demo.studyroom;

import com.example.demo.studyuser.StudyUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Setter
@Getter
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    @NotNull
    // 팀플실이름
    private String name;

    // 수용인원
    @Column
    @NotNull
    private Integer capacity;

    // 예약자명
    @Column
    @NotNull
    private String client;
    // 아직로그인기능을 만들지 않아서 나중에 다시
//    @OneToOne(cascade = CascadeType.REMOVE)
//    private StudyUser client;
}
