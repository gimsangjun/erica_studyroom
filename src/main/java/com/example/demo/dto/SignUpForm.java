package com.example.demo.dto;

import com.example.demo.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@ToString
public class SignUpForm {

    @NotBlank
    private String name; //사용자 이름

    private int age;

    private int grade;

    @Email
    private String email;
    private String university;
    private String department;

    @NotBlank
    private String loginId; //로그인 ID

    @NotBlank
    private String password;


}
