package com.example.demo.test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Slf4j
@Getter
@Setter
// spring security가 제공하는 User 클래스를 우리가 정의한 Member로 사용하기 위해 커스텀함.
public class SecurityUser extends User {

    private Member member;

    public SecurityUser(Member member){
        super(member.getUsername(), member.getPassword() , AuthorityUtils.createAuthorityList(member.getRole().toString()));

        log.info("SecurityUser member.username = {}", member.getUsername());
        log.info("SecurityUser member.password = {}", member.getPassword());
        log.info("SecurityUSer member.role = {}", member.getRole().toString());

        this.member = member;

    }

}
