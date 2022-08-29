package com.example.demo.service;

import com.example.demo.dto.LoginUser;
import com.example.demo.repository.LoginUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginUserService {

    private final LoginUserRepository loginUserRepository;

    // BCrypt를 활용해서 암호화 해야함.
    public LoginUser create(String id, String password){
        LoginUser user = new LoginUser();
        user.setLoginId(id);
        user.setPassword(password);
        this.loginUserRepository.save(user);
        log.info("회원가입 성공");
        return user;
    }

    public LoginUser loadUserByLoginId(String loginId,String password){
        // 람다식으로 작성하는것으 연습해야됨.
        return loginUserRepository.findByloginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
