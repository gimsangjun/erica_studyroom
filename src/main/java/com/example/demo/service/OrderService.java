package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.StudyRoom;
import com.example.demo.domain.User;
import com.example.demo.dto.OrderAPI;
import com.example.demo.repository.OrderRepsitory;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepsitory orderRepsitory;
    private final UserRepository userRepository;

    public void create(Order order){

        this.orderRepsitory.save(order);
    }

    public Optional<Order> getOrder(Long id){return this.orderRepsitory.findById(id);}

    public List<Order> getAllList() {
        return this.orderRepsitory.findAll();
    }

    public void delete(Order order){
        this.orderRepsitory.delete(order);
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
        this.orderRepsitory.save(order);
        return studyRoom;
    }

}
