package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepsitory orderRepsitory;

    public void create(Order order){
        this.orderRepsitory.save(order);
    }

    public void delete(Order order){
        this.orderRepsitory.delete(order);
    }

}
