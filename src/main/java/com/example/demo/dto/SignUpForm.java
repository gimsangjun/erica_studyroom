package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpForm {

    @NotBlank
    private String loginId; //로그인 ID
    @NotBlank
    private String name; //사용자 이름
    @NotBlank
    private String password;
    private int age;
    @Email
    private String email;
    private String university;
    private String department;

}
