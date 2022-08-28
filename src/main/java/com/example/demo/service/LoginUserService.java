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

    public boolean loadUserByLoginId(String LoginId,String password){
        Optional<LoginUser> loginUser = this.loginUserRepository.findByloginId(LoginId);
        if (loginUser.isEmpty()){
            log.info("사용자를 찾을수 없습니다.");
            return false;
        }
        if (loginUser.get().getPassword() == password){
            log.info("로그인 성공");
            return true;
        } else{
            log.info("비밀번호 틀렸습니다.");
            return false;
        }
    }
}
