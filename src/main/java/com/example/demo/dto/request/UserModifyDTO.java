package com.example.demo.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserModifyDTO {

    @NotBlank // String 형태에만 사용,
    private String username; // 사용자 id

    // 비밀번호 변경은 나중에에
//   @NotBlank
//    private String password;

    @NotBlank
    private String name; // 사용자이름

    @NotNull
    private Integer age;

    @NotNull
    @Range(min=1, max=10)
    private Integer grade;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String university;
    @NotBlank
    private String department;



}
