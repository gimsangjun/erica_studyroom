package com.example.demo.domain;

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
    @Column(name = "study_room_id")
    private Long id;

    // 팀플실이름
    @Column(length = 200 , nullable = false)
    private String name;

    private String location;

    @OneToOne(mappedBy = "studyRoom", fetch = FetchType.LAZY)
    private Order order;

    // 수용인원
    @Column(nullable = false)
    @Range(min = 1 , max = 20)
    private int capacity;

}
