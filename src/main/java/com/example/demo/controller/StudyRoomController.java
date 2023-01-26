package com.example.demo.controller;

import com.example.demo.DataNotFoundException;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.dto.request.StudyRoomDTO;
import com.example.demo.dto.response.StduyRoomListResponseDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/studyRoom")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    /**
     * TODO: 오류발생
     * @return 모든 팀플실을 리스트형태로 반환
     */
    @GetMapping()
    public ResponseEntity<Object> read(){
        final StduyRoomListResponseDTO stduyRoomListResponseDTO = StduyRoomListResponseDTO.builder()
                .studyRoomList(studyRoomService.getAllStudyRoom()).build();
        return ResponseEntity.ok(stduyRoomListResponseDTO);
    }
    /**
     *
     * @return 해당 팀플실 detail, 날짜만 넘어오면 해당 날짜의 에약현황 리턴
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id , @RequestParam Optional<Integer> year,
                                         @RequestParam Optional<Integer> month, @RequestParam Optional<Integer> date){

        // TODO: 대부분의 로직 나중에 Service부분으로 옮겨야됨
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        StudyRoomDTO studyRoomDTO = modelMapper.map(studyRoom, StudyRoomDTO.class);
        List<Order> orders = studyRoom.getOrder();

        // 예약내용 추가 - reservation
        ArrayList<LinkedHashMap> list = new ArrayList<>();
        
        if (year.isPresent() && month.isPresent() && date.isPresent()){
            // log.info("query test year={} month={} date={}",year.get(),month.get(),date.get());

            for(Order order : orders){
                LinkedHashMap reservation = new LinkedHashMap<>();
                // TODO: 지금 if문으로 하나씩 체크하고있는데, 나중에 쿼리문을 공부해서 그것으로 바꿔야할듯
                // TODO: 시간대 별로 정렬을 해야하는데 정렬이 아직 안됨.
                if (year.get() == order.getYear() && month.get() == order.getMonth() && date.get() == order.getDate()){
                    reservation.put("orderId",order.getId());
                    reservation.put("year",order.getYear());
                    reservation.put("month",order.getMonth());
                    reservation.put("date",order.getDate());
                    reservation.put("name",order.getUser().getNickname());
                    reservation.put("startTime",order.getStartTime());
                    reservation.put("endTime",order.getEndTime());
                    list.add(reservation);
                }
            }

        } else { // Parameter가 넘어오지 않았을 때
            // log.info("Null query");
            for(Order order : orders){
                LinkedHashMap reservation = new LinkedHashMap<>();
                reservation.put("orderId",order.getId());
                reservation.put("year",order.getYear());
                reservation.put("month",order.getMonth());
                reservation.put("date",order.getDate());
                reservation.put("name",order.getUser().getNickname());
                reservation.put("startTime",order.getStartTime());
                reservation.put("endTime",order.getEndTime());
                list.add(reservation);
            }
        }

        LinkedHashMap map = new LinkedHashMap();
        map.put("info",studyRoomDTO);
        map.put("reservation",list);
        return ResponseEntity.ok(map);
    }

    /**
     * 팀플실 생성
     */
    @PostMapping
    public ResponseEntity<Object> createStudyRoom(@Valid @RequestBody StudyRoomDTO studyRoomDTO){
        StudyRoom studyRoom = new StudyRoom();
        studyRoom = modelMapper.map(studyRoomDTO,StudyRoom.class);
        this.studyRoomService.create(studyRoom);
        return ResponseEntity.ok(studyRoom);
    }

    /**
     * 팀플실 수정
     */
    @PutMapping ("/{id}")
    public ResponseEntity modify(@RequestBody StudyRoomDTO studyRoomDTO, @PathVariable("id") Long id){
        // TODO: 조금더 고급적인 방법이 있는지
        try{
            StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
            this.studyRoomService.modify(studyRoom,studyRoomDTO);
            // TODO: studyRoom을 return 하고싶은데 stackoverflow생겨서 이 문제 쉽게 해결하는 방법알면 다시 수정
            return ResponseEntity.ok("수정성공");
        } catch (DataNotFoundException e){ // 다른예외는 어떻게 처리해야하는지 잘모루
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 팀플실입니다.");
        }

    }
    /**
     * 팀플실 삭제.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        // TODO: 조금더 고급적인 방법이 있는지
        try{
            StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
            this.studyRoomService.delete(studyRoom);
            return ResponseEntity.ok("삭제성공");
        } catch (DataNotFoundException e){ // 다른예외는 어떻게 처리해야하는지 잘모루
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 팀플실입니다.");
        }

    }

    /**
     * 팀플실예약
     */
    @PostMapping("/{id}")
    public ResponseEntity reserve(@RequestBody OrderDTO orderDTO, @PathVariable("id") Long id){
        // TODO: 조금더 고급적인 방법이 있는지
        try{ // TODO : 김영한님 예외 처리 강의 다시 잘 들어야할듯.
            StudyRoom studyRoom = studyRoomService.getStudyRoom(id);

            // 해당 날짜의 예약이 이미 차있는지 확인
            if(!studyRoomService.check(studyRoom,orderDTO)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
            }

            orderService.reserve(studyRoom,orderDTO);
            // TODO: studyRoom을 return 하고싶은데 stackoverflow생겨서 이 문제 쉽게 해결하는 방법알면 다시 수정
            return ResponseEntity.ok("예약완료");
        } catch (DataNotFoundException e){ // 다른예외는 어떻게 처리해야하는지 잘모루
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 팀플실입니다.");
        }
    }

}
