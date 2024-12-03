package com.example.burrrrng.config;

import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.OrderStatus;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @PostConstruct
    public void init() {

        User user = new User("email@email.com", "password", "name", "address", UserRole.USER);

        userRepository.save(user);

        Store store = new Store(user, "storeName", LocalDateTime.now(), LocalDateTime.now(), 12000, StoreStatus.OPENED);

        storeRepository.save(store);

        Order order = new Order(user, store, OrderStatus.UNCHECKED);

        orderRepository.save(order);
    }
}
