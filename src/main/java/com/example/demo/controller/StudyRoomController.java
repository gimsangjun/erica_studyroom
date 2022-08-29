package com.example.demo.controller;

import com.example.demo.dto.StudyRoom;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/studyRoom")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @GetMapping("/detail/{id}")
    public String studyRoomDetail(Model model, @PathVariable("id") int id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom",studyRoom);
        return "studyRoom_detail";
    }

    @GetMapping("/create")
    public String studyRoomForm(Model model){
        model.addAttribute("studyRoom",new StudyRoom());
        return "studyRoom_form";
    }

    @PostMapping("/create")
    public String studyRoomCreate(@Validated @ModelAttribute StudyRoom studyRoom, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("studyRoomCreate Error={}",bindingResult);
            return "studyRoom_form";
        }
        this.studyRoomService.create(studyRoom);
        return "redirect:/";
    }

    // detail기능과 합쳐보기. 점프트 스프링부트
    @GetMapping ("/modify/{id}")
    public String studyRoomModifyForm(Model model, @PathVariable("id") Integer id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom",studyRoom);
        return "studyRoom_modify";
    }

    @PostMapping ("/modify/{id}")
    public String studyRoomModify(@Validated @ModelAttribute StudyRoom studyRoom,BindingResult bindingResult,@PathVariable("id") Integer id){
        if(bindingResult.hasErrors()){
            return String.format("/studyRoom/modify/%s",id);
        }
        StudyRoom room = this.studyRoomService.getStudyRoom(id);
        this.studyRoomService.modify(room,studyRoom.getName(),studyRoom.getCapacity(),studyRoom.getClient());
        return String.format("redirect:/studyRoom/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String studyRoomDelete(@PathVariable("id") Integer id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        this.studyRoomService.delete(studyRoom);
        return "redirect:/";

    }
}
