package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.domain.User;
import com.example.demo.domain.StudyRoom;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final StudyRoomService studyRoomService;

    @RequestMapping("/")
    public String root(@SessionAttribute(name=SessionConst.LOGIN_USER, required = false) User user, Model model){

        // 기본적으로 로그인 하든 안하든, 목록을 보여줌.
        List<StudyRoom> studyRoomList = this.studyRoomService.getList();
        model.addAttribute("studyRoomList",studyRoomList);

        // 세션에 회원 데이터가 없으면 home
        if (user == null){
            return "home";
        }

        // 세션이 유지되면 model에 유저 데이터를 넣어줌.
        model.addAttribute("user", user);
        //log.info("HomeController user= {}", user);
        // home_login 로그인 사용자 전용
        return "home_login";
    }

}
