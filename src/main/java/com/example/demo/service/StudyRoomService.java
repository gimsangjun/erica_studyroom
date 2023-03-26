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
import org.springframework.data.jpa.domain.Specification;
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

    public ArrayList<StudyRoom> findAll(){
        List<StudyRoom> temps = this.studyRoomRepository.findAll();
        ArrayList<StudyRoom> studyRooms = new ArrayList<StudyRoom>();
        studyRooms.addAll(temps);
        return studyRooms;
    }

    public List<StudyRoom> findByCriteria(String university, String building) {
        Specification<StudyRoom> specification = Specification.where(null);

        if (university != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("university"), university));
        }
        if (building != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("building"), building));
        }
        return studyRoomRepository.findAll(specification);
    }
    // 스터디룸 생성
    public StudyRoom create(StudyRoomDTO studyRoomDTO){
        StudyRoom studyRoom = new StudyRoom();
        studyRoom = modelMapper.map(studyRoomDTO, StudyRoom.class);
        this.studyRoomRepository.save(studyRoom);
        return studyRoom;
    }

    // 스터디룸 삭제
    public void delete(StudyRoom studyRoom) {
        this.studyRoomRepository.delete(studyRoom);
    }

    // 스터디룸 수정
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
        List<Order> orders = this.studyRoomRepository.findByDateBetweenTime(studyRoom, orderDTO.getDate(), orderDTO.getStartTime(), orderDTO.getEndTime());
        if (orders.size() > 0) return false;
        else return true;
    }


}
