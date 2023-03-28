package com.example.demo.controller;

import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthController 클래스의 ")
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock // 가짜 객체를 만들어 반환해주는 어노테이션
    private AuthController authController;

    //  @Spy : Stub하지 않은 메소드들은 원본 메소드 그대로 사용하는 어노테이션
    @InjectMocks // @Mock 또는 @Spy로 생성된 가짜 객체를 자동으로 주입시켜주는 어노테이션
    private UserService userService ;

    // 컨트롤러를 테스트하기 위해서는 HTTP호출이 필요, 일반적인 방법으로는 HTTP호출이 불가능하므로 스프링에서 이를 위한 MockMVC를 제공
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }


    @Test
    @DisplayName("회원가입 성공")
    void signUpTest(){
        // given(준비) : 어떠한 데이터가 준비되었을 떄
        SignUpRequest request = signUpRequest("test", "홍길동");
        // when(실행) : 어떠한 함수를 실행하면


        // then(검증) : 어떠한 결과가 나와야 한다.

    }
    private SignUpRequest signUpRequest(final String username, final String name) {
        return SignUpRequest.builder()
                .username(username)
                .password("test")
                .name(name)
                .studentNumber(2018123123)
                .imgUrl("/test/img1.png")
                .age(25)
                .grade(4)
                .email("test@naver.com")
                .university("소프트웨어융합대학")
                .department("컴퓨터학부")
                .build();
    }

    @Test
    void createAuthenticationToken() {
    }
}