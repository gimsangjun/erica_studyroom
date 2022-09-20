package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // BCrypt를 활용해서 암호화 해야함.
    public User create(User user){
        this.userRepository.save(user);
        log.info("회원가입 성공");
        return user;
    }

    public User loadUserByLoginId(String loginId, String password){
        // 람다식으로 작성하는것도 연습해야됨.
        return userRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public User getUserByLoginId(String id){
        // null처리 => Option<> 이부분 다시정리.
        return userRepository.findByLoginId(id).orElse(null);
    }
}
