package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    private String name; // 사용자 이름

    private int age;

    private int grade;

    @Email
    private String email;
    private String university;
    private String department;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Column(unique = true)
    @NotBlank
    private String loginId; // 로그인 ID

    @NotBlank
    private String password;


}
