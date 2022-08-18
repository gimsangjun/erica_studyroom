package com.example.demo.studyuser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class StudyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(255) default '공백'")
    private String name;

    // 재학중
    @Column(columnDefinition = "varchar(255) default '휴학중'")
    private String attending;

    // 학년
    @Column(columnDefinition = "integer default 1")
    private Integer grade;

    // 예약정보

    // 이용현황

}
