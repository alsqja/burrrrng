package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    
    List<OrderMenu> findByOrder(Order order);
}
