package com.example.demo;

import com.example.demo.studyroom.StudyRoom;
import com.example.demo.studyroom.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {

    /*
    @ModelAttribute("studyRooms")
    public List<StudyRoom> users(){
        List<StudyRoom> studyRooms = new ArrayList<>();
        studyRooms.add(new StudyRoom(1,"room1", 10));
        studyRooms.add(new StudyRoom(2,"room2", 20));
        return studyRooms;
    }
    */

    private final StudyRoomService studyRoomService;

    @RequestMapping("/")
    public String root(){
        return "redirect:/studyRoom_list";
    }

    @GetMapping("/studyRoom_list")
    public String studyRoomList(Model model){
        List<StudyRoom> studyRoomList = this.studyRoomService.getList();
        model.addAttribute("studyRoomList",studyRoomList);
        return "studyRoom_list";
    }

    @GetMapping("/studyRoom_list/detail/{id}")
    public String studyRoomDetail(@PathVariable("id") int id){
        return "studyRoom_detail";
    }

    /**
     * 정리해야하는거
     * repository , service, DTO
     *
     */

    /**
     * 구현해야하는거
     * 데이터 조금더 다채롭게
     * 로그인
     * 데이터베이스
     */

}
