package com.example.burrrrng.service;

import com.example.burrrrng.dto.CartMenuResDto;
import com.example.burrrrng.dto.OrderMenuResDto;
import com.example.burrrrng.dto.RequestMenuCreateDto;
import com.example.burrrrng.dto.RequestMenuUpdateDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.OrderMenu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.enums.OrderStatus;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.SameMenuException;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OrderMenuRepository;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.burrrrng.service.StoreServiceTest.getMockHttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMenuRepository orderMenuRepository;

    @Test
    void 메뉴생성_예외() {
        User user = userRepository.save(new User("test@email.com", "0000", "testUserName", "testUserAddress", UserRole.OWNER));

        MockHttpServletRequest request = getMockHttpServletRequest(user);

        Store store = new Store(user, "testStoreName", LocalTime.of(10, 0), LocalTime.of(22, 0), 12000, StoreStatus.OPENED);
        Store savedStore = storeRepository.save(store);

        RequestMenuCreateDto dto = new RequestMenuCreateDto("testMenuName", 12000);

        CommonResDto<ResponseMenuDto> menu = menuService.createMenu(savedStore.getId(), dto, request);

        Optional<Menu> findMenu = menuRepository.findById(menu.getData().getId());
        SameMenuException e = assertThrows(SameMenuException.class, () -> menuService.createMenu(user.getId(), dto, request));

        assertThat(findMenu.isPresent()).isEqualTo(true);
        assertThat(findMenu.get().getName()).isEqualTo("testMenuName");
        assertThat(e.getMessage()).isEqualTo("같은 이름의 메뉴가 이미 존재합니다.");
    }

    @Test
    void 메뉴수정_예외() {
        User user = userRepository.save(new User("test@email.com", "0000", "testUserName", "testUserAddress", UserRole.OWNER));

        MockHttpServletRequest request = getMockHttpServletRequest(user);

        Store store = new Store(user, "testStoreName", LocalTime.of(10, 0), LocalTime.of(22, 0), 12000, StoreStatus.OPENED);
        Store savedStore = storeRepository.save(store);

        Menu menu = menuRepository.save(new Menu(user, savedStore, "testMenuName", 12000, MenuStatus.NORMAL));

        RequestMenuUpdateDto dto = new RequestMenuUpdateDto("testPatchMenuName", 13000, MenuStatus.SOLDOUT);
        CommonResDto<ResponseMenuDto> updatedMenu = menuService.updateMenu(savedStore.getId(), menu.getId(), dto, request);

        Menu findMenu = menuRepository.findById(updatedMenu.getData().getId()).orElse(null);
        assertThat(updatedMenu.getData().getName()).isEqualTo(findMenu.getName());
        assertThat(updatedMenu.getData().getPrice()).isEqualTo(findMenu.getPrice());
        assertThat(updatedMenu.getData().getStatus()).isEqualTo(findMenu.getStatus().getValue());

        dto = new RequestMenuUpdateDto("testPatchMenuName2", 15000, null);

        findMenu = menuRepository.findById(updatedMenu.getData().getId()).orElse(null);
        assertThat(updatedMenu.getData().getName()).isEqualTo(findMenu.getName());
        assertThat(updatedMenu.getData().getPrice()).isEqualTo(findMenu.getPrice());
        assertThat(MenuStatus.SOLDOUT).isEqualTo(findMenu.getStatus());
    }

    @Test
    void 단일주문내역상세메뉴() {
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

        List<OrderMenuResDto> result = menuService.findAllStoreOrderMenus(store.getId(), order.getId(), user.getId());

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getAmount()).isEqualTo(3);
        assertThat(result.get(0).getMenuName()).isEqualTo("menuName1");
    }
}
