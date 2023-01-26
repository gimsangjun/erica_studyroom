package com.example.demo.controller;


import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.User;
import com.example.demo.dto.request.JwtRequest;
import com.example.demo.dto.request.SignUpDTO;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.dto.response.UserListResponseDTO;
import com.example.demo.enums.role.UserRole;
import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService ;
    private final ModelMapper modelMapper;
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
        return userService.isUsernameDuplicated(signUpDTO.getUsername())
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(new JwtResponse(jwtTokenUtils.generateToken(userService.signUp(signUpDTO))));
    }

    @GetMapping("/header")
    public ResponseEntity<String> header(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
        return  ResponseEntity.ok("hello");
    }

    /**
     * 로그인을 하면 JWT토큰발행
     *
     * @return
     */
    @CrossOrigin(origins = "*" , allowedHeaders = {"authorization", "content-type", "x-auth-token"} , methods = RequestMethod.POST, exposedHeaders = "x-auth-token")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest,
                                /* header 테스트용 */                       @RequestHeader Map<String, String> headers) throws Exception{
        /* header 테스트용 */
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });

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

    private void authenticate(String email, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        } catch (BadCredentialsException e){ // 비밀번호 안맞음.
            throw new Exception("INVALID_CREDENTIALS",e);
        }

    }


    /**
     * @return 유저정보
     */
    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET, allowedHeaders = "authorization,content-type,x-auth-token",allowCredentials = "true")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> read(@PathVariable("id") String id,
            /* header 테스트용 */                   @RequestHeader Map<String, String> headers){

        /* header 테스트용 */
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });

        User user = userService.getUserByUsername(id);
        // API validation부분 다시 정리.
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = new UserDTO();
        userDTO = modelMapper.map(user,UserDTO.class);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    // 이런식으로도 쓸수 있다. 흡수하자.
    @GetMapping
    public ResponseEntity<UserListResponseDTO> findAll() {
        // TODO: 오류발생, 모든 Order는 잘됨. 왜 이렇게는 안될까?
        final UserListResponseDTO userListResponseDTO = UserListResponseDTO.builder()
                .userList(userService.findAll()).build();
//        User user = userService.getUserByUsername("test");
//        ArrayList list = new ArrayList();
//        list.add(user);
//        UserListResponseDTO userListResponseDTO = UserListResponseDTO.builder()
//                .userList(list)
//                .build();

        return ResponseEntity.ok(userListResponseDTO);
    }

}
