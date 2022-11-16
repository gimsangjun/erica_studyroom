package com.example.demo.restcontroller;


import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.dto.OrderAPI;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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

    private final OrderService orderService;
    private final StudyRoomService studyRoomService;

    /**
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
        if(!order.isPresent()) {
            // TODO: 조금더 고급적으로 바꿀수 있나 확인
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약입니다.");
        }

        // TODO: stackoverflow문제 때문에 이렇게 했는데 나중에 어떻게 해결할것인지
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
        if(!order.isPresent()) {
            // TODO: 조금더 고급적으로 바꿀수 있나 확인
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약입니다.");
        }
        orderService.delete(order.get());

        return ResponseEntity.ok("delete 성공");
    }

    /**
     * 예약수정
     *
     * @return
     * [
     *     {
     *         "orderId": 1,
     *         "studyRoomId": 1,
     *         "studyRoomName": "큐브0",
     *         "name": "홍길동",
     *         "year": 2022,
     *         "month": 10,
     *         "date": 30,
     *         "startTime": 15,
     *         "endTime": 17
     *     }
     * ]
     */
    @PutMapping("/{id}")
    public ResponseEntity modify(@PathVariable("id") Long id , @RequestBody OrderAPI orderAPI){
        // TODO: validation , 그 order가 있는지, 해당날짜의 그 시간이 비어있는지
        Optional<Order> order = orderService.getOrder(id);
        if(!order.isPresent()) {
            // TODO: 조금더 고급적으로 바꿀수 있나 확인
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약입니다.");
        }

        // 해당 날짜의 예약이 이미 차있는지 확인
        if(!studyRoomService.check(order.get().getStudyRoom(),orderAPI)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
        }

        Order modify = this.orderService.modify(order.get(),orderAPI);

        // TODO: stackoverflow문제 때문에 이렇게 했는데 나중에 어떻게 해결할것인지
        LinkedHashMap response = new LinkedHashMap<>();
        response.put("orderId",modify.getId());
        response.put("studyRoomId",modify.getStudyRoom().getId());
        response.put("name",modify.getUser().getName());
        response.put("year",modify.getYear());
        response.put("month",modify.getMonth());
        response.put("date",modify.getDate());
        response.put("startTime",modify.getStartTime());
        response.put("endTime",modify.getEndTime());

        return ResponseEntity.ok(response);
    }
}
