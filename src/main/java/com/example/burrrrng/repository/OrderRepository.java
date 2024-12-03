package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface OrderRepository extends JpaRepository<Order, Long> {
    default Order findByIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 주문 입니다."));
    }
}
