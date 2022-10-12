package com.example.demo.restcontroller;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.StudyRoomAPI;
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

@RestController // restcontroller에 대해서
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/studyRoom")
public class StudyRoomRestController {

    private final StudyRoomService studyRoomService;
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
     * @retun
     * {
     * 	"info" : {
     * 		"university" : "소프트웨어융합대학",
     * 		"department" : "소프트웨어학부",
     * 		"studyroom_name" : "큐브 0",
     * 		"location" : "3공학관 1층",
     * 		"capacity" : 10,
     *        }
     * 	"reservation" : [
     *            {
     * 				"user" : "홍길동"
     * 				"time" : "15:00 ~ 17:00"
     *            },
     *            {
     * 				"user" : "광개토대왕"
     * 				"time" : "17:00 ~ 19:00"
     *            }
     * 	]
     * }
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id){
        // 대부분의 로직 나중에 Service부분으로 옮겨야됨.
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        StudyRoomAPI studyRoomAPI = modelMapper.map(studyRoom,StudyRoomAPI.class);
        List<Order> orders = studyRoom.getOrder();

        // 예약내용 추가 - reservation
        ArrayList<LinkedHashMap> list = new ArrayList<>();
        for(Order order : orders){
            LinkedHashMap reservation = new LinkedHashMap<>();
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

    /*
    * 리소스 등록
     */
    @PostMapping
    public ResponseEntity<Object> createStudyRoom(@Valid @RequestBody StudyRoomAPI studyRoomAPI){
        StudyRoom studyRoom = new StudyRoom();
        log.info("studyRoomAPI={}",studyRoomAPI);
        studyRoom = modelMapper.map(studyRoomAPI,StudyRoom.class);
        log.info("studyRoom={}",studyRoom);
        this.studyRoomService.create(studyRoom);
        studyRoomAPI.setId(studyRoom.getId());
        return ResponseEntity.ok(studyRoomAPI);
    }

    /*
    * resource를 수정
     */
    @PutMapping ("/{id}")
    public ResponseEntity modify(@RequestBody StudyRoomAPI studyRoomAPI, @PathVariable("id") Long id){
        // TODO : validation 처리, studyRoom이 있는지 없는지
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        this.studyRoomService.modify(studyRoom,studyRoomAPI);
        return ResponseEntity.ok("수정성공");
    }
    /*
    * resource를 삭제.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        // TODO: validation 처리, 있는지 없는지
        StudyRoom studyRoom = studyRoomService.getStudyRoom(id);
        this.studyRoomService.delete(studyRoom);
        return ResponseEntity.ok("삭제성공");
    }
}
