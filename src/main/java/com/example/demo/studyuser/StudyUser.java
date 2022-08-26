package com.example.demo.studyuser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
public class StudyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column
    private String userId;

    @NotBlank
    @Column
    private String password;

    // 재학중

    // 학년


    // 예약정보

    // 이용현황

}
