package com.example.demo.restcontroller;

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
    @GetMapping("/{userId}")
    public ResponseEntity<Object> read(@PathVariable("userId") String id){
        //TODO: 해당유저의 인포 리턴
        return ResponseEntity.ok("test");
    }

}
