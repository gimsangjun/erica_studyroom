package com.example.demo.restcontroller;

import com.example.demo.domain.User;
import com.example.demo.dto.StudyRoomAPI;
import com.example.demo.dto.UserAPI;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // restcontroller에 대해서
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService ;
    private final ModelMapper modelMapper;

    /**
     * @return 유저정보
     * 	"user_id" : "aabbcde",
     * 	"user_name" : "홍길동",
     * 	"age" : 15,
     * 	"grade" : 3,
     * 	"email" : "ddee@gmail.com",
     * 	"university" : "소프트웨어융합대학",
     * 	"department" : "소프트웨어학부"
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserAPI> read(@PathVariable("id") String id){
        User user = userService.getUserByLoginId(id);
        // API validation부분 다시 정리.
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserAPI userAPI = new UserAPI();
        userAPI = modelMapper.map(user,UserAPI.class);
        return new ResponseEntity<UserAPI>(userAPI, HttpStatus.OK);
    }

}
