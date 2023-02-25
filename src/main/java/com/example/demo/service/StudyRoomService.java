package com.example.demo.service;

import com.example.demo.DataNotFoundException;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.dto.request.StudyRoomDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final OrderRepository orderRepository;
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

    public ArrayList<StudyRoom> findAll(){
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

    public StudyRoom modify(StudyRoom studyRoom, StudyRoomDTO studyRoomDTO) {
        studyRoom.update(studyRoomDTO);
        return this.studyRoomRepository.save(studyRoom);
    }

    /**
     * 이미 해당 날짜의 그 시간에, 예약이 차있는 지 확인
     * @param studyRoom : 예약할 팀플실
     * @param orderDTO : 예약할 시간
     */
    public boolean check(StudyRoom studyRoom, OrderDTO orderDTO){
        // TODO: 쿼리문이 어떻게 나가는지 체크
        // 해당 스터디룸의 그 날짜의 예약내용을 가져옴.
        List<Order> orders = orderRepository.findByStudyRoomAndDate(studyRoom, orderDTO.getDate());
        int[] timeCheck = new int[48];
        for(Order order : orders){
            for( float i = order.getStartTime() ; i < order.getEndTime() ; i = (float) (i + 0.5)){
                // TODO: 정리 ,실수형을 정수형으로 바꿀때 조심. 어떤것이 먼저 연산되는지
                int index = (int) (i * 2);
                timeCheck[index] = 1;
            }
        }

        // 이미 그시간이 예약이 되어있으면 false을 리턴.
        for( float i = orderDTO.getStartTime() ; i < orderDTO.getEndTime(); i = (float) (i+ 0.5)){
            int index = (int) (i * 2);
            if (timeCheck[index] == 1) {
                return false;
            }
        }
        return true;
    }

}
