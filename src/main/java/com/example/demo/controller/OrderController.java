package com.example.demo.controller;


import com.example.demo.domain.Order;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, allowedHeaders = {"authorization", "content-type","ngrok-skip-browser-warning"},exposedHeaders = "authorization",allowCredentials = "true", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

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
        // TODO: 조금더 효율적인 방법은 없을까?
        ArrayList<LinkedHashMap> list = new ArrayList<>();
        for(Order order : orders){
            LinkedHashMap reservation = new LinkedHashMap<>();
            reservation.put("orderId",order.getId());
            reservation.put("studyRoomId",order.getStudyRoom().getId());
            reservation.put("studyRoomName",order.getStudyRoom().getName());
            reservation.put("id",order.getUser().getUsername());
            reservation.put("name",order.getUser().getNickname());
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
        // TODO: 더 효율적인 방법으로
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId",order.get().getId());
        reservation.put("studyRoomId",order.get().getStudyRoom().getId());
        reservation.put("studyRoomName",order.get().getStudyRoom().getName());
        reservation.put("id",order.get().getUser().getUsername()); // ID
        reservation.put("name",order.get().getUser().getNickname()); // 이름
        reservation.put("date",order.get().getDate());
        reservation.put("startTime",order.get().getStartTime());
        reservation.put("endTime",order.get().getEndTime());

        return ResponseEntity.ok(reservation);
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
     */
    @PutMapping("/{id}")
    public ResponseEntity modify(@PathVariable("id") Long id , @RequestBody OrderDTO orderDTO){
        // TODO: validation , 그 order가 있는지, 해당날짜의 그 시간이 비어있는지
        Optional<Order> order = orderService.getOrder(id);
        if(!order.isPresent()) {
            // TODO: 조금더 고급적으로 바꿀수 있나 확인
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약입니다.");
        }

        // 해당 날짜의 예약이 이미 차있는지 확인
        if(!studyRoomService.check(order.get().getStudyRoom(), orderDTO)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
        }

        Order modify = this.orderService.modify(order.get(), orderDTO);

        // TODO: stackoverflow문제 때문에 이렇게 했는데 나중에 어떻게 해결할것인지
        // TODO: 더 효율적인 방법으로
        LinkedHashMap reservation = new LinkedHashMap<>();
        reservation.put("orderId",modify.getId());
        reservation.put("studyRoomId",modify.getStudyRoom().getId());
        reservation.put("id",order.get().getUser().getUsername()); // ID
        reservation.put("name",order.get().getUser().getNickname()); // 이름
        reservation.put("date",modify.getDate());
        reservation.put("startTime",modify.getStartTime());
        reservation.put("endTime",modify.getEndTime());

        return ResponseEntity.ok(reservation);
    }
}
