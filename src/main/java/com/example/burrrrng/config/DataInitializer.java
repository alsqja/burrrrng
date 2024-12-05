package com.example.burrrrng.config;

import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @PostConstruct
    public void init() {

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        User user = new User("email@email.com", passwordEncoder.encode("0000"), "name", "address", UserRole.USER);

        userRepository.save(user);

        Store store = new Store(user, "storeName", LocalTime.of(10, 0), LocalTime.of(21, 0), 12000, StoreStatus.OPENED);

        storeRepository.save(store);

//        Order order = new Order(user, store, OrderStatus.UNCHECKED);
//
//        orderRepository.save(order);

//        for (int i = 1; i <= 3; i++) {
//            Menu menu = new Menu(user, store, "menuName" + i, 10000 * i, MenuStatus.NORMAL);
//            menuRepository.save(menu);
//        }
//
//        for (int i = 4; i <= 6; i++) {
//            Menu menu = new Menu(user, store, "menuName" + i, 12000, MenuStatus.SOLDOUT);
//            menuRepository.save(menu);
//        }
    }
}
