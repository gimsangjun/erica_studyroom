package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.dto.LoginUser;
import com.example.demo.dto.StudyRoom;
import com.example.demo.service.StudyRoomService;
import com.example.demo.dto.StudyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final StudyRoomService studyRoomService;

    // @SessionAttribute를 쓰면 조금더 쉽게 처리할수있음.
    @RequestMapping("/")
    public String root(HttpServletRequest request, Model model){

        // 기본적으로 로그인 하든 안하든, 목록을 보여줌.
        List<StudyRoom> studyRoomList = this.studyRoomService.getList();
        model.addAttribute("studyRoomList",studyRoomList);

        // 세션이 없으면 그냥 홈, 새로 만들지도 않음.
        HttpSession session = request.getSession(false);
        if (session == null){
            return "home";
        }

        LoginUser loginUser = (LoginUser) session.getAttribute(SessionConst.LOGIN_USER);
        // 세션에 회원 데이터가 없으면 그냥 홈. - 시간이 지나서 회원데이터가 서버측에서 사라졌을때
        if (session == null) {
            return "home";
        }

        // 세션이 유지되면 model에 유저 데이터를 넣어줌.
        model.addAttribute("loginUser",loginUser);
        return "home";
    }

    /**
     * 정리해야하는거
     * repository , service, DTO
     * 타임리프기능다시 + 부트스트랩
     */

    /**
     * 구현해야하는거
     * 로그인
     * 데이터베이스
     */

}
