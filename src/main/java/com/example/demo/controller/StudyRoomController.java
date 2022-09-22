package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.domain.User;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.StudyRoomForm;
import com.example.demo.dto.ReserveTime;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/studyRoom")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final OrderService orderService;

    @GetMapping("/detail/{id}")
    public String studyRoomDetail(Model model, @PathVariable("id") Long id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        model.addAttribute("studyRoom",studyRoom);
        // log.info("studyRoom ={}",studyRoom);
        // studyRoom =StudyRoom(..., order=com.example.demo.domain.Order@4d1a6c7b, capacity=10)
        return "studyRoom_detail";
    }

    // 예약 기능 아직안됨.연관관계매핑 관련해서 order로 처리해야되서 다음에 하자.
    @GetMapping("/reserve/{id}")
    public String reserve(@SessionAttribute(name= SessionConst.LOGIN_USER, required = false) User user,
                        Model model, @PathVariable("id") Long id){
        ReserveTime reserveTime = new ReserveTime();
        reserveTime.setId(id);
        model.addAttribute("reserveTime",reserveTime);
        return "studyRoom_reserve";

    }

    @PostMapping("/reserve/{id}")
    public String reservePost(@SessionAttribute(name= SessionConst.LOGIN_USER, required = false) User user,
                              @PathVariable("id") Long id, ReserveTime time){

        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);

        Order order =new Order();
        order.setUser(user);
        order.setStudyRoom(studyRoom);
        order.setStartTime(time.getStartTime());
        order.setEndTime(time.getEndTime());
        orderService.create(order);

        log.info("order ={}",order);

        return "redirect:/";

    }


    @GetMapping("/create")
    public String studyRoomForm(Model model){
        model.addAttribute("studyRoomForm",new StudyRoomForm());
        return "studyRoom_form";
    }

    @PostMapping("/create")
    public String studyRoomCreate(StudyRoomForm form){
        // validation 나중에 적용.
//        if(bindingResult.hasErrors()){
//            log.info("studyRoomCreate Error={}",bindingResult);
//            return "studyRoom_form";
//        }
        StudyRoom studyRoom = new StudyRoom();
        log.info("form data={}",form);
        studyRoom.setName(form.getName());
        studyRoom.setCapacity(form.getCapacity());
        studyRoom.setLocation(form.getLocation());

        this.studyRoomService.create(studyRoom);

        return "redirect:/";
    }

    // detail기능과 합쳐보기. 점프트 스프링부트
//    @GetMapping ("/modify/{id}")
//    public String studyRoomModifyForm(Model model, @PathVariable("id") Integer id){
//        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
//        model.addAttribute("studyRoom",studyRoom);
//        return "studyRoom_modify";
//    }
//
//    @PostMapping ("/modify/{id}")
//    public String studyRoomModify(@Validated @ModelAttribute StudyRoom studyRoom,BindingResult bindingResult,@PathVariable("id") Integer id){
//        if(bindingResult.hasErrors()){
//            return String.format("/studyRoom/modify/%s",id);
//        }
//        StudyRoom room = this.studyRoomService.getStudyRoom(id);
//        this.studyRoomService.modify(room,studyRoom.getName(),studyRoom.getCapacity(),studyRoom.getorder());
//        return String.format("redirect:/studyRoom/detail/%s", id);
//    }

    @GetMapping("/delete/{id}")
    public String studyRoomDelete(@PathVariable("id") Long id){
        StudyRoom studyRoom = this.studyRoomService.getStudyRoom(id);
        this.studyRoomService.delete(studyRoom);
        return "redirect:/";

    }
}
