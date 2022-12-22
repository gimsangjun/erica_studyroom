package com.example.demo.test.request;

import com.example.demo.test.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    private String username;
    private String password;
    private Role role;
}
