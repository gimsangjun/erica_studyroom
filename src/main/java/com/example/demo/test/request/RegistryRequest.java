package com.example.demo.test.request;

import com.example.demo.test.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
// view와 controller 간 데이터 인터페이싱을 위해 사용한 요청객체
public class RegistryRequest {

    private String username;
    private String password;
    private Role role = Role.ROLE_MANAGER;
}
