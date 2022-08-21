package com.example.demo;

import com.example.demo.studyroom.StudyRoom;
import com.example.demo.studyroom.StudyRoomRepository;
import com.example.demo.studyroom.StudyRoomService;
import com.example.demo.studyuser.StudyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final StudyRoomService studyRoomService;

    @RequestMapping("/")
    public String root(){
        return "redirect:/studyRoom/list";
    }

    @GetMapping("/studyRoom/list")
    public String studyRoomList(Model model){
        List<StudyRoom> studyRoomList = this.studyRoomService.getList();
        model.addAttribute("studyRoomList",studyRoomList);
        return "studyRoom_list";
    }

    @GetMapping("/studyRoom/detail/{id}")
    public String studyRoomDetail(Model model,@PathVariable("id") int id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom",studyRoom);
        return "studyRoom_detail";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        return "login";
    }

    @GetMapping("/studyRoom/create")
    public String studyRoomForm(){
        return "studyRoom_form";
    }

    @PostMapping("/studyRoom/create")
    public String studyRoomCreate(@ModelAttribute StudyRoom studyRoom){
        this.studyRoomService.create(studyRoom);
        return "redirect:/studyRoom/list";
    }


    /**
     * 정리해야하는거
     * repository , service, DTO
     * 타임리프기능다시
     */

    /**
     * 구현해야하는거
     * 로그인
     * 데이터베이스
     * 삭제기능
     */

}
