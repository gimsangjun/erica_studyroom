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

    // Builder로 Entity DTO 전환할려고했는데 너무 복잡함. ModelMapper오류를 고쳐야 할듯.
//    @Builder
//    public SignUpForm(String name, int age, int grade,String email,String university,String department,String loginId,String password){
//        this.name =name;
//        this.age = age;
//        this.grade = grade;
//        this.email = email;
//        this.university = university;
//        this.department = department;
//        this.loginId = loginId;
//        this.password = password;
//    }
//
//    public User toEntity(){
//        return User.builder()
//                .name(name)
//                .age(age)
//                .grade(grade)
//                .email(email)
//                .university(university)
//                .department(department)
//                .loginId(loginId)
//                .password(password)
//                .build();
//
//    }

}
