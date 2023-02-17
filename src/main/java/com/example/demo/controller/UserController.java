package com.example.demo.controller;

import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.request.SignUpDTO;
import com.example.demo.dto.request.UserModifyDTO;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

// allowedHeaders의 "ngrok-skip-browser-warning"는 ngrok의 "warn page"로 인한 오류 땜에 추가
@CrossOrigin(origins = {"http://localhost:3000"}, methods = {RequestMethod.GET, RequestMethod.PUT}, allowedHeaders = {"authorization", "content-type","ngrok-skip-browser-warning"},exposedHeaders = "authorization",allowCredentials = "true", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService ;
    private final ModelMapper modelMapper;


    // 특정 유저의 정보 출력
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> read(@PathVariable("id") String id){
        User user = userService.getUserByUsername(id);
        // API validation부분 다시 정리.
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = new UserDTO();
        userDTO = modelMapper.map(user,UserDTO.class);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    // 모든 유저리스트 리턴
    @GetMapping
    public ResponseEntity<Object> findAll() {

        List<User> users = userService.findAll();
        ArrayList<UserResponseDTO> list = new ArrayList<>();
        // TODO: 조금더 효율적인 방법은 없을까? 위와 같은 방법
        // entity의 ToString을 어떻게 바꿔볼려고 햇으나 자꾸 스택오버플로우 일어남.
        for(User user : users){
            UserResponseDTO userResponseDTO = modelMapper.map(user,UserResponseDTO.class);
            list.add(userResponseDTO);
        }

        return ResponseEntity.ok(list);
    }

    // 유저의 본인 정보 수정
    @PutMapping("/modify")
    public ResponseEntity<Object> modify(Authentication authentication, @RequestBody final UserModifyDTO dto){

        // 현재 로그인한 유저객체를 가져옴
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();

        // 현재 로그인한 유저와 요청을 온 body의 user가 같은지 체크
        if (user.getUsername().equals(dto.getUsername())){
            userService.modify(user, dto);
            return ResponseEntity.ok("수정완료");
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }
    }

    // 자기자신의 유저정보 리턴
    @GetMapping("/info")
    public ResponseEntity info(Authentication authentication){

        // 현재 로그인한 유저 객체를 가져옴
        User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return ResponseEntity.ok(user.info());

    }

    // 자기자신의 예약내용 리턴턴
   @GetMapping("/order")
    public ResponseEntity order(Authentication authentication){
       // 현재 로그인한 유저 객체를 가져옴
       User user = ((MyUserDetails) authentication.getPrincipal()).getUser();
       List<Order> orders = this.userService.gerOrder(user);
       if (orders.size() == 0){
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body("유저의 예약 내용은 없습니다.");
       }else {
           ArrayList<LinkedHashMap> list = new ArrayList<>();
           for(Order order : orders){
               list.add(order.getResponse());
           }
           return ResponseEntity.ok(list);
       }

   }
}
