package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.request.SignUpDTO;
import com.example.demo.enums.role.UserRole;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public User signUp(final SignUpDTO signUpDTO){
        final User user = modelMapper.map(signUpDTO,User.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        return userRepository.save(user);
    }

    // 이미 그 유저 네임이 존재하는지
    public boolean isUsernameDuplicated(final String username){
        return userRepository.existsByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String id){
        // null처리 => Option<> 이부분 다시정리.
        return userRepository.findByUsername(id).orElse(null);
    }
}
