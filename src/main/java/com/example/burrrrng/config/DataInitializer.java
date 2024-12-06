package com.example.burrrrng.config;

import com.example.burrrrng.dto.CartMenuResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.OrderMenu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.enums.OrderStatus;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OrderMenuRepository;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderMenuRepository orderMenuRepository;

    @PostConstruct
    public void init() {

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        User user = new User("email@email.com", passwordEncoder.encode("0000"), "name", "address", UserRole.OWNER);

        userRepository.save(user);

        Store store = new Store(user, "storeName", LocalTime.of(10, 0), LocalTime.of(21, 0), 12000, StoreStatus.OPENED);

        storeRepository.save(store);

        Order order = new Order(user, store, OrderStatus.UNCHECKED);

        orderRepository.save(order);

        List<Menu> menus = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Menu menu = new Menu(user, store, "menuName" + i, 10000 * i, MenuStatus.NORMAL);
            menuRepository.save(menu);
            menus.add(menuRepository.save(menu));
        }

        for (int i = 4; i <= 6; i++) {
            Menu menu = new Menu(user, store, "menuName" + i, 12000, MenuStatus.SOLDOUT);
            menuRepository.save(menu);
        }

        List<CartMenuResDto> cartItems = menus.stream().map(i -> new CartMenuResDto(
                i.getId(),
                i.getName(),
                i.getPrice() * 3,
                3
        )).toList();

        for (int i = 0; i < menus.size(); i++) {
            OrderMenu orderMenu = new OrderMenu(order, menus.get(i), cartItems.get(i).getAmount());
            orderMenuRepository.save(orderMenu);
        }

    }
}
