package com.example.demo.restcontroller;

import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.StudyRoomAPI;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController // restcontroller에 대해서
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/studyRoom")
public class StudyRoomController {

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
     *
     * @return 해당 팀플실 정보와 예약현황
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id){
        //TODO: 팀플실 찾고 info랑 현황 리턴
        return ResponseEntity.ok("test");
    }


}
