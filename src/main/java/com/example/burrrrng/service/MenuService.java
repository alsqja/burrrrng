package com.example.burrrrng.service;


import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.OrderMenuResDto;
import com.example.burrrrng.dto.RequestMenuCreateDto;
import com.example.burrrrng.dto.RequestMenuUpdateDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonNoContentResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.exception.MenuNotFoundException;
import com.example.burrrrng.exception.SameMenuException;
import com.example.burrrrng.exception.StoreNotFoundException;
import com.example.burrrrng.exception.UnauthorizedException;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OrderMenuRepository;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.OwnerStoreRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final OwnerStoreRepository ownerStoreRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    public CommonResDto<ResponseMenuDto> createMenu(Long id, RequestMenuCreateDto requestMenuCreateDto, HttpServletRequest request) {

        Store store = storeRepository.findByIdOrElseThrow(id);

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreNotFoundException("폐업된 가게입니다.");
        }

        if (!Objects.equals(store.getUser().getId(), user.getId())) {
            throw new UnauthorizedException("본인 가게의 메뉴만 작성 가능합니다");
        }

        menuRepository.findByStoreAndName(store, requestMenuCreateDto.getName()).ifPresent(menu -> {
            throw new SameMenuException("같은 이름의 메뉴가 이미 존재합니다.");
        });

        MenuStatus status = MenuStatus.NORMAL;
        Menu newMenu = new Menu(user, store, requestMenuCreateDto.getName(), requestMenuCreateDto.getPrice(), status);

        Menu savedMenu = menuRepository.save(newMenu);

        ResponseMenuDto responseMenuDto = new ResponseMenuDto(
                newMenu.getId(),
                newMenu.getName(),
                newMenu.getPrice(),
                newMenu.getStatus().getValue()
        );

        return new CommonResDto<>("메뉴생성완료", responseMenuDto);
    }

    public CommonResDto<ResponseMenuDto> updateMenu(Long storeId, Long menuId, RequestMenuUpdateDto requestMenuUpdateDto, HttpServletRequest request) {

        Store store = storeRepository.findByIdOrElseThrow(storeId);

        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreNotFoundException("폐업된 가게입니다.");
        }

        Menu menu = menuRepository.findByStoreAndId(store, menuId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 메뉴 입니다."));

        List<Menu> menus = menuRepository.findByStoreId(storeId);
        for (Menu m : menus) {
            if (m.getDeletedAt() == null && m.getName().equals(requestMenuUpdateDto.getName())) {
                throw new SameMenuException("같은 이름을 가진 메뉴가 이미 있습니다.");
            }
        }

        if (requestMenuUpdateDto.getName() != null && !requestMenuUpdateDto.getName().trim().isEmpty()) {
            menu.updateName(requestMenuUpdateDto.getName());
        }

        if (requestMenuUpdateDto.getPrice() != 0) {
            menu.updatePrice(requestMenuUpdateDto.getPrice());
        }

        if (requestMenuUpdateDto.getStatus() != null) {
            menu.updateStatus(requestMenuUpdateDto.getStatus());
        }

        Menu updateMenu = menuRepository.save(menu);

        ResponseMenuDto responseMenuDto = new ResponseMenuDto(
                updateMenu.getId(),
                updateMenu.getName(),
                updateMenu.getPrice(),
                updateMenu.getStatus().getValue()
        );

        return new CommonResDto<>("메뉴 수정 완료", responseMenuDto);
    }

    public ResponseEntity<CommonNoContentResDto> deleteMenu(Long storeId, Long menuId, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        Store store = storeRepository.findByIdOrElseThrow(storeId);

        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreNotFoundException("폐업된 가게입니다.");
        }

        Menu menu = menuRepository.findByStoreIdAndMenuIdAndUserId(storeId, menuId, user.getId()).orElseThrow(() -> new MenuNotFoundException("삭제할 메뉴가 없습니다."));

        menuRepository.delete(menu);

        return ResponseEntity.ok(new CommonNoContentResDto("메뉴 삭제 완료"));
    }

    public List<OrderMenuResDto> findAllStoreOrderMenus(Long storeId, Long orderId, Long id) {

        Order order = orderRepository.findByIdOrElseThrow(orderId);

        Store store = storeRepository.findByIdOrElseThrow(storeId);

        if (!order.getStore().getUser().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인 가게의 주문 내역만 받아올 수 있습니다.");
        }

        if (!order.getStore().getId().equals(store.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보와 가게가 일치하지 않습니다.");
        }

        return orderMenuRepository.findByOrder(order).stream().map(i -> new OrderMenuResDto(
                i.getId(),
                i.getOrder().getId(),
                i.getMenu().getId(),
                i.getMenu().getName(),
                i.getAmount(),
                i.getCreatedAt(),
                i.getUpdatedAt()
        )).toList();
    }
}
