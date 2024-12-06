package com.example.burrrrng.service;

import com.example.burrrrng.dto.CartMenuResDto;
import com.example.burrrrng.dto.OrderAllResDto;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMenuRepository orderMenuRepository;

    @Test
    void 주문내역받아오기_예외() throws JsonProcessingException, UnsupportedEncodingException {
        User owner = userRepository.save(new User("test@email.com", "0000", "testOwnerName", "testOwnerAddress", UserRole.OWNER));
        User user = userRepository.save(new User("test1@email.com", "0000", "testUserName", "testUserAddress", UserRole.USER));

        Store store = storeRepository.save(new Store(user, "testStoreName", LocalTime.of(10, 0), LocalTime.of(22, 0), 12000, StoreStatus.OPENED));

        List<Menu> menus = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Menu menu = new Menu(user, store, "menuName" + i, 10000 * i, MenuStatus.NORMAL);
            menus.add(menuRepository.save(menu));
        }

        List<CartMenuResDto> cartItems = menus.stream().map(i -> new CartMenuResDto(
                i.getId(),
                i.getName(),
                i.getPrice() * 3,
                3
        )).toList();

        Order order = orderRepository.save(new Order(user, store, OrderStatus.UNCHECKED));

        for (int i = 0; i < menus.size(); i++) {
            OrderMenu orderMenu = new OrderMenu(order, menus.get(i), cartItems.get(i).getAmount());
            orderMenuRepository.save(orderMenu);
        }

        List<OrderAllResDto> result = orderService.findAllUserOrder(user.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(order.getId());
        assertThat(result.get(0).getMainMenu()).isEqualTo(cartItems.get(2).getName());
        assertThat(result.get(0).getTotalMenuCount()).isEqualTo(3);
        assertThat(result.get(0).getTotalPrice()).isEqualTo(180000);
    }
}
