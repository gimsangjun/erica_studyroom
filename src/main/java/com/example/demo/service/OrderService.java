package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.OrderAPI;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public StudyRoom reserve(StudyRoom studyRoom, OrderAPI orderAPI){

        //임시로 유저 이름만  만듬.
        User user = new User();
        user.setName(orderAPI.getUser());
        user.setLoginId("test");
        user.setPassword("password");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStudyRoom(studyRoom);
        order.setYear(orderAPI.getYear());
        order.setMonth(orderAPI.getMonth());
        order.setDate(orderAPI.getDate());
        order.setStartTime(orderAPI.getStartTime());
        order.setEndTime(orderAPI.getEndTime());
        this.orderRepository.save(order);
        return studyRoom;
    }

    public Order modify(Order order, OrderAPI api){
        order.setReserveYear(api.getYear());
        order.setReserveMonth(api.getMonth());
        order.setReserveDate(api.getDate());
        order.setStartTime(api.getStartTime());
        order.setEndTime(api.getEndTime());
        this.orderRepository.save(order);
        return order;
    }

}
