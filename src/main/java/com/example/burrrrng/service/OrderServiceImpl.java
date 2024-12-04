package com.example.burrrrng.service;

import com.example.burrrrng.config.CartCookieEncoder;
import com.example.burrrrng.dto.CartMenuResDto;
import com.example.burrrrng.dto.OrderResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.OrderMenu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.enums.OrderStatus;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OrderMenuRepository;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartCookieEncoder cartCookieEncoder;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public OrderResDto save(HttpServletRequest request, HttpServletResponse response, Long id) {

        List<CartMenuResDto> cartMenus = cartCookieEncoder.getCartFromCookies(request);

        List<Long> menuIds = cartMenus.stream().map(CartMenuResDto::getId).toList();
        List<Menu> menus = menuRepository.findAllById(menuIds).stream().filter(i -> i.getStatus().equals(MenuStatus.NORMAL)).toList();

        if (menuIds.size() != menus.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "품절된 상품 입니다.");
        }

        Long storeId = 0L;
        for (Menu menu : menus) {
            if (!menu.getStore().getId().equals(storeId) && !storeId.equals(0L)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "한 가게의 상품만 주문할 수 있습니다");
            }
            storeId = menu.getStore().getId();
        }

        User user = userRepository.findByIdOrElseThrow(id);
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        Order order = new Order(user, store, OrderStatus.UNCHECKED);
        Order savedOrder = orderRepository.save(order);

        int totalPrice = 0;

        for (int i = 0; i < menus.size(); i++) {
            OrderMenu orderMenu = new OrderMenu(savedOrder, menus.get(i), cartMenus.get(i).getAmount());
            orderMenuRepository.save(orderMenu);
            totalPrice += cartMenus.get(i).getPrice();
        }

        cartCookieEncoder.deleteCartCookie(response);

        return new OrderResDto(savedOrder.getId(), savedOrder.getStatus(), totalPrice, savedOrder.getCreatedAt(), savedOrder.getUpdatedAt());
    }
}
