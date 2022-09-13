package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // BCrypt를 활용해서 암호화 해야함.
    public Member create(String id, String password, String name){
        Member user = new Member();
        user.setLoginId(id);
        user.setPassword(password);
        user.setName(name);
        this.memberRepository.save(user);
        log.info("회원가입 성공");
        return user;
    }

    public Member loadUserByLoginId(String loginId, String password){
        // 람다식으로 작성하는것으 연습해야됨.
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
