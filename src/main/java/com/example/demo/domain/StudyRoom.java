package com.example.demo.domain;

import com.example.demo.dto.StudyRoomAPI;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;


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
    @Column(length = 200, nullable = false)
    private String name;

    private String university; // 소프트웨어융합대학
    private String department; // 소프트웨어학부

    @NotBlank
    private String location;

    @OneToMany(mappedBy = "studyRoom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> order;

    // 수용인원
    @Column(nullable = false)
    @Range(min = 1, max = 20)
    private int capacity;

    // 예약된 횟수
    @ColumnDefault("0")
    private Integer count ;

    // 설명
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "study_room_descrptions", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> descriptions = new HashSet<>(); // ex. 투명한 벽으로 구분된 경상대학 팀플실

    // 주의사항 , 리스트 여야됨.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "study_room_caution", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> cautions = new HashSet<>(); // ex. 음식물 반입금지, 시끄럽게 떠드는 행위 금지

    // 허용음료
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "study_room_drinks", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> drinks = new HashSet<>(); // ex. 커피, 물

    // 태그
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "study_room_tags", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> tags = new HashSet<>();

    public void update(StudyRoomAPI studyRoomAPI){
        this.name = studyRoomAPI.getStudyroom_name();
        this.university = studyRoomAPI.getUniversity();
        this.department = studyRoomAPI.getDepartment();
        this.location = studyRoomAPI.getLocation();
        this.capacity = studyRoomAPI.getCapacity();
        this.descriptions = studyRoomAPI.getDescriptions();
        this.cautions = studyRoomAPI.getCautions();
        this.drinks = studyRoomAPI.getDrinks();
        this.tags = studyRoomAPI.getTags();

    }

    public Integer countUp(){
        return this.count++;
    }

}

