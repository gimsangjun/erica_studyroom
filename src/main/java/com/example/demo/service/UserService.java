package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.dto.request.UserModifyRequest;
import com.example.demo.enums.role.UserRole;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public User signUp(final SignUpRequest signUpRequest){
        final User user = modelMapper.map(signUpRequest,User.class);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
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
        Optional<User> user = userRepository.findByUsername(id);
        if (user.isPresent()){
            return user.get();
        } else{
            throw new DataNotFoundException("존재하지 않는 유저입니다.");
        }

    }

    // 유저 정보 수정
    public User modify(User user, UserModifyRequest dto){
        user.update(dto);
        user.setUpdateAt(LocalDateTime.now());
        return this.userRepository.save(user);
    }

}
