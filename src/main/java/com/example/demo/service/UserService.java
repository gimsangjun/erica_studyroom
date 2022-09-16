package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // BCrypt를 활용해서 암호화 해야함.
    public User create(String id, String password, String name){
        User user = new User();
        user.setLoginId(id);
        user.setPassword(password);
        user.setName(name);
        this.userRepository.save(user);
        log.info("회원가입 성공");
        return user;
    }

    public User loadUserByLoginId(String loginId, String password){
        // 람다식으로 작성하는것으 연습해야됨.
        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
