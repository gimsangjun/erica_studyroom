package com.example.demo.controller;


import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            list.add(order.getResponse());
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
        return ResponseEntity.ok(order.get().getResponse());
    }

    /**
     * 예약삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(Authentication authentication ,@PathVariable("id") Long id){
        Optional<Order> order_ = orderService.getOrder(id);
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();
        if(!order_.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약입니다.");
        }
        Order order = order_.get();
        // 로그인한 유저와 order의 유저가 같은 사람인지 확인
        if(!order.getUser().getUsername().equals(user.getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }

        orderService.delete(order);

        return ResponseEntity.ok("delete 성공");
    }

    /**
     * 예약수정
     */
    @PutMapping("/{id}")
    public ResponseEntity modify(Authentication authentication, @PathVariable("id") Long id , @RequestBody OrderDTO orderDTO){
        Optional<Order> order_ = orderService.getOrder(id);
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();

        if(!order_.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약입니다.");
        }
        Order order = order_.get();

        // 로그인한 유저와 order의 유저가 같은 사람인지 확인
        if(!order.getUser().getUsername().equals(user.getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }

        // 해당 날짜의 예약이 이미 차있는지 확인
        if(!studyRoomService.check(order.getStudyRoom(), orderDTO)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
        }

        Order modify = this.orderService.modify(order, orderDTO);

        return ResponseEntity.ok(modify.getResponse());
    }

}
