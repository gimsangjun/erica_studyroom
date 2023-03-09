package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.request.OrderDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    // 특정 팀플실의 특정날짜의 order를 리턴
    public List<Order> getByStudyRoomAndDate(StudyRoom studyRoom, LocalDate date){
        List<Order> orders = orderRepository.findByStudyRoomAndDate(studyRoom,date);
        return orders;
    }

    public List<Order> getByStudyRoom(StudyRoom studyRoom){
        List<Order> orders = orderRepository.findByStudyRoom(studyRoom);
        return orders;
    }


    public List<Order> getAllList() {
        return this.orderRepository.findAll();
    }

    public void delete(Order order){
        this.orderRepository.delete(order);
    }

    public StudyRoom reserve(StudyRoom studyRoom, OrderDTO orderDTO, String username){
        User user = this.userRepository.findByUsername(username).get();
        Order order = new Order();
        order.changeUser(user);
        order.changeStudyRoom(studyRoom);
        order.setDate(orderDTO.getDate());
        order.setStartTime(orderDTO.getStartTime());
        order.setEndTime(orderDTO.getEndTime());
        this.orderRepository.save(order);
        return studyRoom;
    }

    public Order modify(Order order, OrderDTO orderDTO){
        order.setDate(orderDTO.getDate());
        order.setStartTime(orderDTO.getStartTime());
        order.setEndTime(orderDTO.getEndTime());
        this.orderRepository.save(order);
        return order;
    }

}
