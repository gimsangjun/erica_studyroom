package com.example.demo.restcontroller;

import com.example.demo.dto.StudyRoomAPI;
import com.example.demo.dto.UserAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<Object> read(@PathVariable("id") String id){
        UserAPI user = new UserAPI();
        user.setUser_id(id);
        user.setUser_name("홍길동");
        user.setAge(15);
        user.setGrade(3);
        user.setEmail("ddee@gmail.com");
        user.setUniversity("소프트웨어융합대학");
        user.setDepartment("소프트웨어학부");
        return ResponseEntity.ok(user);
    }

}
