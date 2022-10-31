package com.example.demo.restcontroller;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.OrderAPI;
import com.example.demo.dto.RerserveDate;
import com.example.demo.dto.StudyRoomAPI;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/studyRoom")
public class StudyRoomRestController {

    private final StudyRoomService studyRoomService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    /**
     *
     * @return 모든 팀플실을 리스트형태로 반환
     */
    @GetMapping()
    public ResponseEntity<Object> read(){
        ArrayList<StudyRoom> studyRooms = studyRoomService.getAllStudyRoom();
        ArrayList<StudyRoomAPI> studyRoomAPI = new ArrayList<StudyRoomAPI>();
        studyRooms.stream()
                .forEach(
                        studyRoom -> {
                            studyRoomAPI.add(modelMapper.map(studyRoom,StudyRoomAPI.class));
                        }
                );
        return ResponseEntity.ok(studyRoomAPI);
    }
    /**
     *
     * @return 해당 팀플실 detail
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id , @RequestBody RerserveDate day){
        // 대부분의 로직 나중에 Service부분으로 옮겨야됨.
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        StudyRoomAPI studyRoomAPI = modelMapper.map(studyRoom,StudyRoomAPI.class);
        List<Order> orders = studyRoom.getOrder();

        // 생각해보니 이렇게 filter하는 기능은 나중에 넣는게 좋을듯.
        // log.info("Day ={}",day);

        // 예약내용 추가 - reservation
        ArrayList<LinkedHashMap> list = new ArrayList<>();
        for(Order order : orders){
            LinkedHashMap reservation = new LinkedHashMap<>();
            reservation.put("year",order.getYear());
            reservation.put("month",order.getMonth());
            reservation.put("date",order.getDate());
            reservation.put("name",order.getUser().getName());
            reservation.put("startTime",order.getStartTime());
            reservation.put("endTime",order.getEndTime());
            list.add(reservation);
        }

        LinkedHashMap map = new LinkedHashMap();
        map.put("info",studyRoomAPI);
        map.put("reservation",list);
        return ResponseEntity.ok(map);
    }

    /**
     * 팀플실 생성
     */
    @PostMapping
    public ResponseEntity<Object> createStudyRoom(@Valid @RequestBody StudyRoomAPI studyRoomAPI){
        StudyRoom studyRoom = new StudyRoom();
        studyRoom = modelMapper.map(studyRoomAPI,StudyRoom.class);
        this.studyRoomService.create(studyRoom);
        studyRoomAPI.setId(studyRoom.getId());
        return ResponseEntity.ok(studyRoomAPI);
    }

    /**
     * 팀플실 수정
     */
    @PutMapping ("/{id}")
    public ResponseEntity modify(@RequestBody StudyRoomAPI studyRoomAPI, @PathVariable("id") Long id){
        // TODO : validation 처리, studyRoom이 있는지 없는지
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        this.studyRoomService.modify(studyRoom,studyRoomAPI);
        return ResponseEntity.ok("수정성공");
    }
    /**
     * 팀플실 삭제.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        // TODO: validation 처리: 있는지 없는지
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        this.studyRoomService.delete(studyRoom);
        return ResponseEntity.ok("삭제성공");
    }

    /**
     * 팀플실예약
     */
    @PostMapping("/{id}")
    public ResponseEntity reserve(@RequestBody OrderAPI orderAPI, @PathVariable("id") Long id){
        // TODO: validation 처리 : 팀플실이 있는지 없는지, 해당시간이 이미 예약이 되있는지.
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        orderService.reserve(studyRoom,orderAPI);
        return ResponseEntity.ok("테스트");
    }

}
