package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.dto.request.UserModifyDTO;
import com.example.demo.enums.role.UserRole;
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
        // null처리 => Option<> 이부분 다시정리.
        return userRepository.findByUsername(id).orElse(null);
    }

    // 유저 정보 수정
    public User modify(User user, UserModifyDTO dto){
        user.update(dto);
        user.setUpdateAt(LocalDateTime.now());
        return this.userRepository.save(user);
    }

    // 유저의 예약내용 리턴
    public List<Order> gerOrder(User user){
        return this.orderRepository.findByUser(user);
    }

    public List<Order> getOrderByDate(User user, LocalDate date){
        return this.orderRepository.findByUserAndDate(user, date);

    }

}
