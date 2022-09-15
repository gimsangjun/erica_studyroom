package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotBlank
    private String name; // 사용자 이름

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @Column(unique = true)
    @NotBlank
    private String loginId; // 로그인 ID

    @NotBlank
    private String password;
}
