package com.example.demo.dto.request;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
@ToString
public class SignUpDTO {

    @NotBlank // String 형태에만 사용,
    private String username; // 사용자 id

    @NotBlank
    private String password;

    @NotBlank
    private String nickname; // 사용자이름

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
