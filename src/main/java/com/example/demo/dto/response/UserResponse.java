package com.example.demo.dto.response;

import lombok.Data;


@Data
public class UserResponse {

    private String username ; // aabbcde
    private String name; // 홍길동
    private Integer studentNumber; // 학번
    private String imgUrl;
    private Integer age; // 15
    private Integer grade; // 3
    private String email; // ddee@gmail.com -> 나중에 클래스라고 해야되나 따로 형식있는걸로 업그레이드
    private String university; // 소프트웨어융합대학
    private String department; // 소프트웨어학부

}
