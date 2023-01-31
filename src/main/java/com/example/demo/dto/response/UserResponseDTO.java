package com.example.demo.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserResponseDTO {

    private String username;
    private String nickname;
    private String age;
    private String grade;
    private String email;
    private String university;
    private String department;

}
