package com.example.demo.test.controller;

import com.example.demo.test.Member;
import com.example.demo.test.MemberRepository;
import com.example.demo.test.request.RegistryRequest;
import com.example.demo.test.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller()
public class RegistryController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/test/registry")
    public String registryForm(Model model){
        model.addAttribute("member",new RegistryRequest());
        return "/test/registration";
    }

    @PostMapping("/test/registry")
    public String registry(@ModelAttribute RegistryRequest registryRequest){
        Member member = Member.builder()
                .username(registryRequest.getUsername())
                .password(passwordEncoder.encode(registryRequest.getPassword()))
                .role(registryRequest.getRole())
                .build();
        memberRepository.save(member);
        return "redirect:/test/login";
    }

    @ModelAttribute("roles")
    public Map<String, Role> roles(){
        Map<String, Role> map = new LinkedHashMap<>();
        map.put("관리자",Role.ROLE_ADMIN);
        map.put("매니저",Role.ROLE_MANAGER);
        map.put("일반 사용자", Role.ROLE_MEMBER);
        return  map;

    }


}
