package com.example.demo.controller;

import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.User;
import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.request.SignUpDTO;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.enums.role.UserRole;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * @param signUpDTO
     * @return JWT토큰
     */
    @PostMapping("/signUp")
    public ResponseEntity<JwtResponse> signUp(@RequestBody final SignUpDTO signUpDTO){
        log.info("계정정보 ={}",signUpDTO);
        if(userService.isUsernameDuplicated(signUpDTO.getUsername())){
            log.info("유저 중복에러={}",signUpDTO);
        }
        return userService.isUsernameDuplicated(signUpDTO.getUsername())
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(new JwtResponse(jwtTokenUtils.generateToken(userService.signUp(signUpDTO))));
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

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) { // 비밀번호 안맞음.
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}