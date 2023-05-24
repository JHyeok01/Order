package com.example.samp;

import com.example.samp.entity.Orders;
import com.example.samp.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void orderTest(){
        orderRepository.save(Orders.builder()
                .box(10)
                .orderDate(LocalDateTime.now())
                .status("승인")
                .orderFrom("주문자")
                .product("흑마늘즙")
                .build());
    }


}
