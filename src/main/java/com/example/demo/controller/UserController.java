package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

// allowedHeaders의 "ngrok-skip-browser-warning"는 ngrok의 "warn page"로 인한 오류 땜에 추가
@CrossOrigin(origins = {"http://localhost:3000"}, methods = RequestMethod.GET, allowedHeaders = {"authorization", "content-type","ngrok-skip-browser-warning"},exposedHeaders = "authorization",allowCredentials = "true", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService ;
    private final ModelMapper modelMapper;

    /**
     * @return 유저정보
     */

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
}
