package com.example.demo.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SignUpRequest {

    @NotNull
    private final String username; // 사용자 id

    @NotNull
    private final String password;

    @NotNull
    private final String name; // 사용자이름

    @NotNull
    private final Integer studentNumber;

    @NotNull
    private final String imgUrl;

    @NotNull
    private final Integer age;

    @NotNull
    @Range(min=1, max=10)
    private final Integer grade;

    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String university;
    @NotNull
    private final String department;


}
