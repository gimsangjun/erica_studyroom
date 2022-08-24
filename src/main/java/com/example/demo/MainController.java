package com.example.demo;

import com.example.demo.studyroom.StudyRoom;
import com.example.demo.studyroom.StudyRoomRepository;
import com.example.demo.studyroom.StudyRoomService;
import com.example.demo.studyuser.StudyUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String studyRoomForm(Model model){
        model.addAttribute("studyRoom",new StudyRoom());
        return "studyRoom_form";
    }

    @PostMapping("/studyRoom/create")
    public String studyRoomCreate(@Validated @ModelAttribute StudyRoom studyRoom, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("studyCreate Error={}",bindingResult);
            return "studyRoom_form";
        }
        this.studyRoomService.create(studyRoom);
        return "redirect:/studyRoom/list";
    }

    // detail기능과 합쳐보기. 점프트 스프링부트
    @GetMapping ("/studyRoom/modify/{id}")
    public String studyRoomModifyForm(Model model, @PathVariable("id") Integer id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom",studyRoom);
        return "studyRoom_modify";
    }

    @PostMapping ("/studyRoom/modify/{id}")
    public String studyRoomModify(@Validated @ModelAttribute StudyRoom studyRoom,BindingResult bindingResult,@PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return String.format("/studyRoom/modify/%s",id);
        }
        StudyRoom room = this.studyRoomService.getStudyRoom(id);
        this.studyRoomService.modify(room,studyRoom.getName(),studyRoom.getCapacity(),studyRoom.getClient());
        return String.format("redirect:/studyRoom/detail/%s", id);
    }

    @GetMapping("/studyRoom/delete/{id}")
    public String studyRoomDelete(@PathVariable("id") Integer id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        this.studyRoomService.delete(studyRoom);
        return "redirect:/";

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
