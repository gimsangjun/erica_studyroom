package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Setter
@Getter
@ToString
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_room_id")
    private Long id;

    // 팀플실이름
    @Column(length = 200 , nullable = false)
    private String name;

    private String university ; // 소프트웨어융합대학
    private String department ; // 소프트웨어학부

    @NotBlank
    private String location;

    @OneToMany(mappedBy = "studyRoom", fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    private List<Order> order;

    // 수용인원
    @Column(nullable = false)
    @Range(min = 1 , max = 20)
    private int capacity;

}
