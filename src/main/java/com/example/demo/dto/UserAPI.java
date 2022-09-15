package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class UserAPI {

    private String user_id ; // aabbcde
    private String user_name; // 홍길동
    private int age; // 15
    private int grade; // 3
    private String email; // ddee@gmail.com -> 나중에 클래스라고 해야되나 따로 형식있는걸로 업그레이드
    private String university; // 소프트웨어융합대학
    private String department; // 소프트웨어학부
}
