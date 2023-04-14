package com.example.demo.controller;

import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.request.StudyRoomRequest;
import com.example.demo.dto.response.StudyRoomResponse;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, allowedHeaders = {"authorization", "content-type","ngrok-skip-browser-warning"},exposedHeaders = "authorization",allowCredentials = "true", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/studyroom")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    /**
     * @return 팀플실을 리스트형태로 반환
     */
    @GetMapping()
    public ResponseEntity<Object> read(@RequestParam(name = "university", required = false) String university,
                                       @RequestParam(name = "building", required = false) String building){
        ArrayList<StudyRoomResponse> list = new ArrayList<>();
        List<StudyRoom> studyRoomList = studyRoomService.findByCriteria(university, building);

        for (StudyRoom studyRoom : studyRoomList){
            StudyRoomResponse studyRoomResponse = modelMapper.map(studyRoom, StudyRoomResponse.class);
            list.add(studyRoomResponse);
        }
        return ResponseEntity.ok(list);
    }
    /**
     *
     * @return 해당 팀플실 detail, 날짜만 넘어오면 해당 날짜의 에약현황 리턴
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id ,
                                         @RequestParam(name = "date")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date){
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        StudyRoomRequest studyRoomRequest = modelMapper.map(studyRoom, StudyRoomRequest.class);

        List<Order> orders;

        // 예약내용 추가 - reservation
        ArrayList<LinkedHashMap> list = new ArrayList<>();

        // TODO: 그냥 GetMapping처럼 동적으로 생성하게 바꿔야됨. QueryDSL사용해봐야할듯.
        // date가 param형태로 넘어왔다면
        if (date.isPresent()){
            // 해당 팀플실의 특정날짜의 orders 가져오기
            orders = orderService.getByStudyRoomAndDate(studyRoom, date.get());
            for(Order order : orders){
                list.add(orderToResponse(order));
            }

        } else { // Parameter가 넘어오지 않았을 때
            // 특정 팀플실의 예약현황 모두 가져오기
            orders = orderService.getByStudyRoom(studyRoom);
            for(Order order : orders){
                list.add(orderToResponse(order));
            }
        }

        LinkedHashMap map = new LinkedHashMap();
        map.put("info", studyRoomRequest);
        map.put("reservation",list);
        return ResponseEntity.ok(map);
    }

    /**
     * 팀플실 생성
     */
    @PostMapping
    public ResponseEntity<Object> createStudyRoom(@Valid @RequestBody StudyRoomRequest studyRoomRequest){
        return studyRoomService.isNameDuplicated(studyRoomRequest.getName())
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 팀플실 이름입니다.")
                : ResponseEntity.ok(modelMapper.map(this.studyRoomService.create(studyRoomRequest), StudyRoomResponse.class));
    }

    /**
     * 팀플실 수정
     */
    @PutMapping ("/{id}")
    public ResponseEntity modify(@Valid @RequestBody StudyRoomRequest studyRoomRequest, @PathVariable("id") Long id){
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        this.studyRoomService.modify(studyRoom, studyRoomRequest);
        return ResponseEntity.ok("수정성공");
    }
    /**
     * 팀플실 삭제.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        this.studyRoomService.delete(studyRoom);
        return ResponseEntity.ok("삭제성공");
    }

    /**
     * 팀플실예약
     */
    @PostMapping("/{id}")
    public ResponseEntity reserve(Authentication authentication,@Valid @RequestBody OrderRequest orderRequest, @PathVariable("id") Long id){
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        // 예약인원 초가
        if(orderRequest.getBookingCapacity() > studyRoom.getCapacity()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이 팀플실의 예약가능한 인원 : " + studyRoom.getCapacity() + " 예약인원 초과");
        }
        // 해당 날짜의 예약이 이미 차있는지 확인
        if(!studyRoomService.check(studyRoom, orderRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
        }
        // 현재 접근한 사용자의 정보를 가져옴.
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();
        orderService.reserve(studyRoom, orderRequest, user.getUsername());
        return ResponseEntity.ok("예약완료");
    }

    public LinkedHashMap<String,String> orderToResponse(Order order){
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId",order.getId());
        reservation.put("date",order.getDate());
        reservation.put("state", order.getState().getState());
        reservation.put("name",order.getUser().getName());
        reservation.put("startTime",order.getStartTime());
        reservation.put("endTime",order.getEndTime());
        reservation.put("bookingCapacity", order.getBookingCapacity());
        return reservation;
    }
}
