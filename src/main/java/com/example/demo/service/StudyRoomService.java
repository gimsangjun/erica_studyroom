package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.request.StudyRoomRequest;
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
            throw new DataNotFoundException("존재하는 않는 팀플실입니다.");
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
        List<StudyRoom> studyRooms = studyRoomRepository.findAll(specification);
        if (studyRooms.size() == 0){
            throw new DataNotFoundException("팀플실이 존재하지 않습니다.");
        } else{
            return studyRooms;
        }

    }
    public boolean isNameDuplicated(String name){
        return studyRoomRepository.existsByName(name);
    }

    // 스터디룸 생성
    public StudyRoom create(StudyRoomRequest studyRoomRequest){
        StudyRoom studyRoom = new StudyRoom();
        studyRoom = modelMapper.map(studyRoomRequest, StudyRoom.class);
        this.studyRoomRepository.save(studyRoom);
        return studyRoom;
    }

    // 스터디룸 삭제
    public void delete(StudyRoom studyRoom) {
        this.studyRoomRepository.delete(studyRoom);
    }

    // 스터디룸 수정
    public StudyRoom modify(StudyRoom studyRoom, StudyRoomRequest studyRoomRequest) {
        studyRoom.update(studyRoomRequest);
        return this.studyRoomRepository.save(studyRoom);
    }

    /**
     * 이미 해당 날짜의 그 시간에, 예약이 차있는 지 확인
     * @param studyRoom : 예약할 팀플실
     * @param orderRequest : 예약할 시간
     */
    public boolean check(StudyRoom studyRoom, OrderRequest orderRequest){
        List<Order> orders = this.studyRoomRepository.findByDateBetweenTime(studyRoom, orderRequest.getDate(), orderRequest.getStartTime(), orderRequest.getEndTime());
        if (orders.size() > 0) return false;
        else return true;
    }

    // TODO: 좀더 효율적인 방법으로 리팩토링필요
    /**
     * 예약연장을 할 떄, 자기자신은 뺴고 예약이 차있는지 확인
     * 위의 check함수와 합칠려고 했으나, 오류발생(이미 예약되있는데 예약가능)
     * @param studyRoom
     * @param orderRequest
     * @param order
     * @return
     */
    public boolean extendCheck(StudyRoom studyRoom, OrderRequest orderRequest, Order order) {
        List<Order> orders = this.studyRoomRepository.findByDateBetweenTime(studyRoom, orderRequest.getDate(), orderRequest.getStartTime(), orderRequest.getEndTime());
        for(Order o : orders){
            log.info("orders={}", o.getResponse());
        }
        log.info("order={]",order.getResponse());
        //TODO: List에서 remove가 정확히 어떻게 동작하느지
        orders.remove(order);
        for(Order o : orders){
            log.info("orders={}", o.getResponse());
        }
        if (orders.size() > 0) return false;
        else return true;
    }

    /**
     * 현재 시용자가 같은 시간대에 두곳 이상을 예약하고 있으면 안됨.
     * @param orderRequest
     * @param user
     * @return
     */
    public boolean userCheck(OrderRequest orderRequest, User user) {
        List<Order> orders = this.studyRoomRepository.findByDateBetweenTimeAndUser(orderRequest.getDate(), orderRequest.getStartTime(), orderRequest.getEndTime(), user);
        if (orders.size() > 0) return false;
        else return true;

    }
}
