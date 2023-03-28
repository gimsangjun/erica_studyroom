package com.example.demo.controller;

import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.User;
import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.request.SignUpRequest;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.enums.role.UserRole;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService ;
    // 인증할때 필요
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    // jwt 토큰 관련 도구
    private final JwtTokenUtils jwtTokenUtils;

    /**
     * 회원가입
     * @param signUpRequest
     * @return JWT토큰
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody final SignUpRequest signUpRequest){
        return userService.isUsernameDuplicated(signUpRequest.getUsername())
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 유저입니다.")
                : ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(jwtTokenUtils.generateToken(userService.signUp(signUpRequest))));
    }

    /**
     * 로그인을 하면 JWT토큰발행
     *
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception{

        // 비밀번호 등이 틀렸는지 검증.
        authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());

        // MyUserDetails를 리턴함.
        final MyUserDetails userDetails = (MyUserDetails) userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtils.generateToken(User.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .role(UserRole.ROLE_USER)
                .build());

        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) { // 비밀번호 안맞음.
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
