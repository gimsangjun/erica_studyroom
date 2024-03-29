package com.example.demo.domain;

import com.example.demo.enums.OrderState;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@Getter
@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    // 주인: 외래키값이 있는곳, 여기에는 꼭 값을 넣어줘야한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id", nullable = false)
    private StudyRoom studyRoom;
    // order의 상태를 나타내는 state 속성 추가
    // 취소
    // 정상
    // 반납
    @Column(columnDefinition = "varchar(255) default '정상'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderState state = OrderState.NORMAL;

    // 예약날짜
    @Column(nullable = false)
    private LocalDate date;
    // 예약시간.
    @Column(nullable = false)
    private Double startTime;
    @Column(nullable = false)
    private Double endTime;
    // 예약 인원
    @Range(min = 1, message = "최소 인원은 1명이상이어야 합니다.")
    @Column(name = "booking_capacity", nullable = false)
    private Integer bookingCapacity;

    public void setStudyRoom(StudyRoom room){
        this.studyRoom = room;
    }

    public LinkedHashMap<String, String> getResponse(){
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId", id);
        reservation.put("studyRoomId", studyRoom.getId());
        reservation.put("studyRoomName", studyRoom.getName());
        reservation.put("state", state.getState());
        reservation.put("building", studyRoom.getBuilding());
        reservation.put("location", studyRoom.getLocation());
        reservation.put("name", user.getName());
        reservation.put("date", date);
        reservation.put("startTime", startTime);
        reservation.put("endTime", endTime);
        reservation.put("bookingCapacity", bookingCapacity);
        return reservation;
    }

    // 연관관계 편의 메서드
    public void changeUser(User user){
        this.user = user;
        user.getOrders().add(this);
    }

    // 연관관계 편의 메서드
    public void changeStudyRoom(StudyRoom studyRoom){
        this.studyRoom = studyRoom;
        studyRoom.getOrders().add(this);
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(Double startTime, Double endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
