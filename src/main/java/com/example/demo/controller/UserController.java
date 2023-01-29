package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.dto.response.UserListResponseDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
