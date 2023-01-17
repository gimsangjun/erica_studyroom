package com.example.demo.service;

import com.example.demo.DataNotFoundException;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.dto.request.StudyRoomDTO;
import com.example.demo.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final ModelMapper modelMapper;

    public List<StudyRoom> getList() {
        return this.studyRoomRepository.findAll();
    }

    public StudyRoom getStudyRoom(Long id){
        Optional<StudyRoom> studyRoom = this.studyRoomRepository.findById(id);
        if(studyRoom.isPresent()){
            return studyRoom.get();
        }else {
            throw new DataNotFoundException("StudyRoom not found");
        }
    }

    public ArrayList<StudyRoom> getAllStudyRoom(){
        List<StudyRoom> temps = this.studyRoomRepository.findAll();
        ArrayList<StudyRoom> studyRooms = new ArrayList<StudyRoom>();
        studyRooms.addAll(temps);
        return studyRooms;
    }

    public void create(StudyRoom room){
        // TODO: 중복처리
        Optional<StudyRoom> studyRoom = studyRoomRepository.findByName(room.getName());
        this.studyRoomRepository.save(room);
    }

    public void delete(StudyRoom studyRoom) {
        // TODO: 해당 room이 있는지 체크.
        this.studyRoomRepository.delete(studyRoom);
    }

    public void modify(StudyRoom studyRoom, StudyRoomDTO studyRoomDTO) {
        studyRoom.update(studyRoomDTO);
        this.studyRoomRepository.save(studyRoom);
    }

    /**
     * 이미 해당 날짜의 그 시간에, 예약이 차있는 지 확인
     * 해당 함수는
     * StudyRoomRestController - reserve()
     * OrderRestController - delete(), modify() 에서사용
     * @param
     * @param orderDTO
     */
    public boolean check(StudyRoom studyRoom, OrderDTO orderDTO){
        // TODO: 사실 @query문, JPQL로 해결하고 싶은데, 그건 나중에 알아봐야할듯.
        List<Order> orders = studyRoom.getOrder();

        // 24시간이다, 그시간이 이미 사용중이면 1로 표시가 되어있다.
        int[] timeCheck = new int[24]; // 0으로 초기화 되어있다.

        for(Order order : orders){
            //TODO: studyRoomRestController-detail()에서도 똑같이 이런방식으로 했는데, 쿼리문으로 바꿔야함.
            if (orderDTO.getYear() == order.getYear() && orderDTO.getMonth() == order.getMonth() && orderDTO.getDate() == order.getDate()){
                // TODO: 이런식으로 하면 24시에서 날짜가 넘어갈때 오류가 생길듯 하지만, 그건 나중에 생각하자.
                for ( int i = order.getStartTime() ; i < order.getEndTime() ; i++){
                    timeCheck[i] = 1; // 이미 예약중인 것 체크
                }
            }
        }

        for ( int i = orderDTO.getStartTime() ; i < orderDTO.getEndTime() ; i++){
            // 이미 사용중이다.
            if (timeCheck[i] == 1) return false;
        }
        // 테스트
        // log.info("timeCheck={}", timeCheck);
        return true;
    }

}
