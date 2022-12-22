package com.example.demo.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
// 로그인을 위한 username (혹은 id, email) 이 DB에 있는지 확인하는 메서드 loadUserByUsername 메서드를 작성
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByUsername(username);
        if(!findMember.isPresent()) throw new UsernameNotFoundException("존재하지 않는 username 입니다.");
        log.info("loadUserByUsername member.username = {}", username);

        // Custom User인 SecurityUser를 생성하여 반환
        return new SecurityUser(findMember.get());
    }

}
