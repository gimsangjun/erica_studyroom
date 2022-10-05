package com.example.demo.dto;

import com.example.demo.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@ToString
public class SignUpForm {

    @NotBlank // String 형태에만 사용,
    private String name; //사용자 이름

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

    @NotBlank
    private String loginId; //로그인 ID

    @NotBlank
    private String password;

}
