package com.example.demo.test;

import lombok.Getter;

import javax.persistence.GeneratedValue;

@Getter
public enum Role {

    ROLE_ADMIN("관리자"), ROLE_MANAGER("매니저"), ROLE_MEMBER("일반사용자");

    private String descripition;

    Role(String description){
        this.descripition = description;
    }

}
