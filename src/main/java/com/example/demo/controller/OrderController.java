package com.example.demo.controller;

import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.enums.OrderState;
import com.example.demo.service.OrderService;
import com.example.demo.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, allowedHeaders = {"authorization", "content-type","ngrok-skip-browser-warning"},exposedHeaders = "authorization",allowCredentials = "true", maxAge = 3000)
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final StudyRoomService studyRoomService;

    /**
     * @return 모든 예약 돌려줌.
     */
    @GetMapping
    public ResponseEntity<Object> read(@RequestParam(name = "university", required = false) String university,
                                         @RequestParam(name = "building", required = false) String building,
                                         @RequestParam(name = "date", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<Order> orders = orderService.findByCriteria(null, university, building, date, OrderState.NORMAL);
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
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(order.getResponse());
    }

    /**
     * 예약삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(Authentication authentication ,@PathVariable("id") Long id){
        Order order = orderService.getOrder(id);
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();
        // 로그인한 유저와 order의 유저가 같은 사람인지 확인
        orderService.authorizeUser(user, order);
        orderService.delete(order);
        return ResponseEntity.ok("delete 성공");
    }

    /**
     * 예약수정
     */
    @PutMapping("/{id}")
    public ResponseEntity modify(Authentication authentication, @PathVariable("id") Long id ,@Valid @RequestBody OrderRequest orderRequest){
        Order order = orderService.getOrder(id);
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();

        // 로그인한 유저와 order의 유저가 같은 사람인지 확인
        orderService.authorizeUser(user, order);
        // 해당 날짜의 예약이 이미 차있는지 확인
        if(!studyRoomService.check(order.getStudyRoom(), orderRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 예약되어 있습니다.");
        }
        Order modify = this.orderService.modify(order, orderRequest);
        return ResponseEntity.ok(modify.getResponse());
    }

    /**
     * 예약반납
     */
    @PutMapping("/return/{id}")
    public ResponseEntity return_(Authentication authentication, @PathVariable("id") Long id){
        Order order = orderService.getOrder(id);
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser();
        // 로그인한 유저와 order의 유저가 같은 사람인지 확인
        orderService.authorizeUser(user, order);
        this.orderService.return_(order);
        return ResponseEntity.status(HttpStatus.OK).body("반납 완료");
    }

}
