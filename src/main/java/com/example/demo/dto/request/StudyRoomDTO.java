package com.example.demo.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
// 팀플실을 새로 만들때나, 수정할때 사용
public class StudyRoomDTO {
    // TODO: valid처리해야됨.
    private String name ; // 큐브 "0"
    private String university ; // 소프트웨어융합대학
    private String department ; // 소프트웨어학부
    private String location ; // 3공학관 1층
    private int capacity ; // 10
    private Set<String> descriptions = new HashSet<>(); // ex. 투명한 벽으로 구분된 팀플실
    private Set<String> cautions = new HashSet<>(); // ex. 음식물 반입금지, 시끄럽게 떠드는 행위 금지
    private Set<String> drinks = new HashSet<>(); // ex. 커피, 물
    private Set<String> tags = new HashSet<>();

}