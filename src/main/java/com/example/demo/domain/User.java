package com.example.demo.domain;

import com.example.demo.domain.common.Common;
import com.example.demo.dto.request.SignUpDTO;
import com.example.demo.dto.request.UserModifyDTO;
import com.example.demo.enums.role.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@Table(name = "USERS")
@AllArgsConstructor
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

    // 주인: 외래키값이 있는곳, 여기에는 꼭 값을 넣어줘야한다.
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public void update(UserModifyDTO dto){
        this.nickname = dto.getNickname();
        this.age = dto.getAge();
        this.grade = dto.getGrade();
        this.email = dto.getEmail();
        this.university = dto.getUniversity();
        this.department = dto.getDepartment();
    }

    // DTO 출력용용
   public LinkedHashMap<String , String> info(){
        LinkedHashMap info = new LinkedHashMap();
        info.put("username", username);
        info.put("nickname", nickname);
        info.put("age", age);
        info.put("grade", grade);
        info.put("email", email);
        info.put("university", university);
        info.put("department", department);
        return info;
    }


}
