package com.example.demo.domain;

import com.example.demo.domain.common.Common;
import com.example.demo.enums.role.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@Table(name = "USERS")
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Common implements Serializable {

    @Column(unique = true,nullable = false , length = 50)
    private String username; // 로그인 ID

    @Column(nullable = false)
    private String password;

    @NotBlank
    private String nickname; // 사용자 이름

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private int age;

    private int grade;

    @Email
    private String email;
    private String university;
    private String department;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;



}
