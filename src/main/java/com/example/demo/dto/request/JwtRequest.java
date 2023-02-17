package com.example.demo.dto.request;


import javax.validation.constraints.NotBlank;

// 로그인할때 사용
public class JwtRequest {

    @NotBlank
    private String username; // 로그인 아이디

    @NotBlank
    private String password;

    //need default constructor for JSON Parsing
    public JwtRequest()
    {

    }

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
