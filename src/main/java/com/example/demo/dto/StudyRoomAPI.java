package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StudyRoomAPI {
    // TODO: valid처리해야됨.
    private String university ; // 소프트웨어융합대학
    private String department ; // 소프트웨어학부
    private String location ; // 3공학관 1층
    private String studyroom_name ; // 큐브 "0"
    private int capacity ; // 10
}
