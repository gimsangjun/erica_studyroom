package com.example.demo.service;

import com.example.demo.domain.MyUserDetails;
import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void create(Order order){
        this.orderRepository.save(order);
    }

    public Optional<Order> getOrder(Long id){return this.orderRepository.findById(id);}

    public List<Order> getAllList() {
        return this.orderRepository.findAll();
    }

    public void delete(Order order){
        this.orderRepository.delete(order);
    }

    public StudyRoom reserve(StudyRoom studyRoom, OrderDTO orderDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) ((MyUserDetails) authentication.getPrincipal()).getUser(); // MyUserDetails
        // log.info("인증객체에서 유저가 꺼내와지나? Principal={}",user.getUsername());

        Order order = new Order();
        order.setUser(user);
        order.setStudyRoom(studyRoom);
        order.setYear(orderDTO.getYear());
        order.setMonth(orderDTO.getMonth());
        order.setDate(orderDTO.getDate());
        order.setStartTime(orderDTO.getStartTime());
        order.setEndTime(orderDTO.getEndTime());
        this.orderRepository.save(order);
        return studyRoom;
    }

    public Order modify(Order order, OrderDTO orderDTO){
        order.setReserveYear(orderDTO.getYear());
        order.setReserveMonth(orderDTO.getMonth());
        order.setReserveDate(orderDTO.getDate());
        order.setStartTime(orderDTO.getStartTime());
        order.setEndTime(orderDTO.getEndTime());
        this.orderRepository.save(order);
        return order;
    }

}
