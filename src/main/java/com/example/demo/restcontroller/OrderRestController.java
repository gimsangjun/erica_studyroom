package com.example.demo.restcontroller;


import com.example.demo.domain.Order;
import com.example.demo.dto.OrderAPI;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderRestController {

    private final StudyRoomService studyRoomService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    /**
     *
     * @return 모든 예약 돌려줌. 테스트 용도
     */
    @GetMapping
    public ResponseEntity<Object> getAll(){
        // 대부분의 로직 나중에 Service부분으로 옮겨야됨.
        List<Order> orders = orderService.getAllList();

        // 예약내용 추가 - reservation
        ArrayList<LinkedHashMap> list = new ArrayList<>();
        for(Order order : orders){
            LinkedHashMap reservation = new LinkedHashMap<>();
            reservation.put("orderId",order.getId());
            reservation.put("studyRoomId",order.getStudyRoom().getId());
            reservation.put("studyRoomName",order.getStudyRoom().getName());
            reservation.put("name",order.getUser().getName());
            reservation.put("year",order.getYear());
            reservation.put("month",order.getMonth());
            reservation.put("date",order.getDate());
            reservation.put("startTime",order.getStartTime());
            reservation.put("endTime",order.getEndTime());
            list.add(reservation);
        }

        return ResponseEntity.ok(list);
    }
    /**
     * @return 특정 order의 정보를 리턴.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> detail(@PathVariable("id") Long id ){
        // 대부분의 로직 나중에 Service부분으로 옮겨야됨.
        // TODO: validation , order가 비어있을때 처리
        Optional<Order> order = orderService.getOrder(id);

        ArrayList<LinkedHashMap> list = new ArrayList<>();
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId",order.get().getId());
        reservation.put("studyRoomId",order.get().getStudyRoom().getId());
        reservation.put("studyRoomName",order.get().getStudyRoom().getName());
        reservation.put("name",order.get().getUser().getName());
        reservation.put("year",order.get().getYear());
        reservation.put("month",order.get().getMonth());
        reservation.put("date",order.get().getDate());
        reservation.put("startTime",order.get().getStartTime());
        reservation.put("endTime",order.get().getEndTime());
        list.add(reservation);

        return ResponseEntity.ok(list);
    }

    /**
     * 예약삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        // TODO: validation , order가 비어있을때 처리
        Optional<Order> order = orderService.getOrder(id);
        orderService.delete(order.get());

        return ResponseEntity.ok("delete 성공?");
    }

    /**
     * 예약수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> modify(@PathVariable("id") Long id , @RequestBody OrderAPI orderAPI){
        // TODO: validation , 그 order가 있는지, 해당날짜의 그 시간이 비어있는지
        Optional<Order> order = orderService.getOrder(id);
        if(!order.isPresent()) {
            // TODO: 존재하지 않을경우 확인
        }

        order.get().setReserveYear(orderAPI.getYear());
        order.get().setReserveMonth(orderAPI.getMonth());
        order.get().setReserveDate(orderAPI.getDate());
        order.get().setStartTime(orderAPI.getStartTime());
        order.get().setEndTime(orderAPI.getEndTime());

        return ResponseEntity.ok("modify 성공?");
    }
}
