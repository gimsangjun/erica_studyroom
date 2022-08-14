package com.example.demo;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @ModelAttribute("studyRooms")
    public List<StudyRoom> users(){
        List<StudyRoom> studyRooms = new ArrayList<>();
        studyRooms.add(new StudyRoom(1,"room1", 10));
        studyRooms.add(new StudyRoom(2,"room2", 20));
        return studyRooms;
    }

    @RequestMapping("/")
    public String root(){
        return "redirect:/studyRoom_list";
    }

    @GetMapping("/studyRoom_list")
    public String studyRoomList(){
        return "studyRoom_list";
    }

    @GetMapping("/studyRoom_list/detail/{id}")
    public String studyRoomDetail(@PathVariable("id") int id){
        return "studyRoom_detail";
    }

    /**
     * 구현해야하는거
     * 데이터 조금더 다채롭게
     * 로그인
     * 데이터베이스
     */

}
