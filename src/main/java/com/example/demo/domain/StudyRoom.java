package com.example.demo.domain;

import com.example.demo.dto.request.StudyRoomRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.*;

@Table(name = "study_room")
@Entity
@Setter
@Getter
@ToString
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_room_id")
    private Long id;

    @Column(name = "name")
    private String name; // 큐브0
    @Column(name = "university")
    private String university; // 소프트웨어융합대학
    @Column(name = "building")
    private String building; // 건물 : 학연산클러스터지원센터
    @Column(name = "location")
    private String location; // 몇 층 : 5층
    @Column(name = "orders")
    @OneToMany(mappedBy = "studyRoom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
    // 수용인원
    @Column(name = "capacity")
    @Range(min = 1)
    private int capacity;
    // 설명
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "study_room_descriptions", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> descriptions = new HashSet<>(); // ex. 투명한 벽으로 구분된 경상대학 팀플실
    // 주의사항 , 리스트 여야됨.
    @ElementCollection(fetch =  FetchType.LAZY)
    @CollectionTable(name = "study_room_cautions", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> cautions = new HashSet<>(); // ex. 음식물 반입금지, 시끄럽게 떠드는 행위 금지
    // 허용음료
    @ElementCollection(fetch =  FetchType.LAZY)
    @CollectionTable(name = "study_room_drinks", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> drinks = new HashSet<>(); // ex. 커피, 물
    // 태그
    @ElementCollection(fetch =  FetchType.LAZY)
    @CollectionTable(name = "study_room_tags", joinColumns = @JoinColumn(name = "study_room_id"))
    private Set<String> tags = new HashSet<>();

    public void update(StudyRoomRequest studyRoomRequest){
        this.name = studyRoomRequest.getName();
        this.university = studyRoomRequest.getUniversity();
        this.building = studyRoomRequest.getBuilding();
        this.location = studyRoomRequest.getLocation();
        this.capacity = studyRoomRequest.getCapacity();
        this.descriptions = studyRoomRequest.getDescriptions();
        this.cautions = studyRoomRequest.getCautions();
        this.drinks = studyRoomRequest.getDrinks();
        this.tags = studyRoomRequest.getTags();
    }

}

