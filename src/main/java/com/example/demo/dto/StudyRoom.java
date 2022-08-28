package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Setter
@Getter
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 팀플실이름
    @Column(length = 200)
    @NotBlank
    private String name;

    // 수용인원
    @Column
    @NotNull
    @Range(min = 1 , max = 20)
    private Integer capacity;

    // 예약자명
    @Column
    @NotBlank
    private String client;
    // 아직로그인기능을 만들지 않아서 나중에 다시
//    @OneToOne(cascade = CascadeType.REMOVE)
//    private StudyUser client;
}
