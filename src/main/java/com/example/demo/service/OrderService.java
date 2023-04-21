package com.example.demo.service;

import com.example.demo.exception.DataNotFoundException;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.enums.OrderState;
import com.example.demo.exception.UnAuthorizedException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param user : 로그인한 유저의 username(id)
     * @param university
     * @param building
     * @param date
     * @return 조건에 해당하는 order 리턴
     */
    public List<Order> findByCriteria(User user, String university, String building, LocalDate date, OrderState state) {
        Specification<Order> specification = Specification.where(null);

        if (user != null){
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join("user").get("username"), user.getUsername()));
        }
        if (university != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join("studyRoom").get("university"), university));
        }
        if (building != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join("studyRoom").get("building"), building));
        }
        if (date != null){
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("date"), date));
        }
        if (state == OrderState.NORMAL){ // OrderState == "정상"만, "반납", "취소" 상태는 안됨.
            specification = specification.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("state"), OrderState.NORMAL)));
        }
        List<Order> orders = orderRepository.findAll(specification);
        if (orders.size() == 0){
            throw new DataNotFoundException("예약이 존재하지 않습니다.");
        } else{
            return orders;
        }
    }

    public Order getOrder(Long id) {
        Optional<Order> order = this.orderRepository.findById(id);
        if(order.isPresent()) {
            return order.get();
        } else{
            throw new DataNotFoundException("존재 하지 않는 예약입니다.");
        }
    }

    // 내가 선택한 order의 User와 현재 로그인한 유저가 같은지 확인
    public void authorizeUser(User user, Order order){
        if(!order.getUser().getUsername().equals(user.getUsername())) { // TODO: 이 부분도 Exception으로 처리?
            throw new UnAuthorizedException("권한이 없습니다.");
        }
    }

    // 특정 팀플실의 특정날짜의 order를 리턴
    public List<Order> getByStudyRoomAndDate(StudyRoom studyRoom, LocalDate date){
        List<Order> orders = orderRepository.findByStudyRoomAndDate(studyRoom,date);
        return orders;
    }

    public List<Order> getByStudyRoom(StudyRoom studyRoom){
        List<Order> orders = orderRepository.findByStudyRoom(studyRoom);
        return orders;
    }


    public void delete(Order order){
        // this.orderRepository.delete(order);
        order.setState(OrderState.CANCEL);
        this.orderRepository.save(order);
    }

    // 예약
    public StudyRoom reserve(StudyRoom studyRoom, OrderRequest orderRequest, String username){
        User user = this.userRepository.findByUsername(username).get();
        Order order = Order.builder()
                .user(user)
                .date(orderRequest.getDate())
                .startTime(orderRequest.getStartTime())
                .endTime(orderRequest.getEndTime())
                .bookingCapacity(orderRequest.getBookingCapacity())
                .build();
        this.orderRepository.save(order);
        return studyRoom;
    }

    // 수정
    public Order modify(Order order, OrderRequest orderRequest){
        order.setDate(orderRequest.getDate());
        order.setTime(orderRequest.getStartTime(), orderRequest.getEndTime());
        this.orderRepository.save(order);
        return order;
    }

    // 예약 반납
    public void return_(Order order) {
        order.setState(OrderState.RETURN);
        this.orderRepository.save(order);
    }

    // 특정 상태의 order를 모두 가져옴
    public List<Order> getOrderByStateAndDate(OrderState state, LocalDate date) {
        return this.orderRepository.findByStateAndDate(state, date);
    }


}
