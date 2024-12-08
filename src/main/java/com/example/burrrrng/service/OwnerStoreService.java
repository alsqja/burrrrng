package com.example.burrrrng.service;


import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.RequestOrderUpdateDto;
import com.example.burrrrng.dto.RequestOwnerStoreDto;
import com.example.burrrrng.dto.RequestOwnerStoreUpdateDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.ResponseOrderUpdateDto;
import com.example.burrrrng.dto.ResponseOwnerStoreDto;
import com.example.burrrrng.dto.ResponseOwnerStoreUpdateDto;
import com.example.burrrrng.dto.ResponseViewOrderDto;
import com.example.burrrrng.dto.common.CommonListResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.OrderMenu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.exception.StoreLimitException;
import com.example.burrrrng.exception.StoreNotFoundException;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.OwnerStoreRepository;
import com.example.burrrrng.repository.StoreRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerStoreService {

    private final OwnerStoreRepository ownerStoreRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;


    public CommonResDto<ResponseOwnerStoreDto> createStore(RequestOwnerStoreDto requestOwnerStoreDto, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        String name = requestOwnerStoreDto.getName();
        LocalTime openedAt = requestOwnerStoreDto.getOpenedAt();
        LocalTime closedAt = requestOwnerStoreDto.getClosedAt();
        int minPrice = requestOwnerStoreDto.getMinPrice();
        StoreStatus status = StoreStatus.OPENED;

        long storeCount = storeRepository.countByUserAndStatusNot(user, StoreStatus.CLOSED);
        if (storeCount >= 3) {
            throw new StoreLimitException("폐업 상태가 아닌 가게를 3개 이상 운영할 수 없습니다.");
        }

        Store newStore = new Store(user, name, openedAt, closedAt, minPrice, status);

        Store savedStore = ownerStoreRepository.save(newStore);

        ResponseOwnerStoreDto responseOwnerStoreDto = new ResponseOwnerStoreDto(
                savedStore.getId(),
                savedStore.getName(),
                savedStore.getMinPrice(),
                savedStore.getStatus().name(),
                savedStore.getOpenedAt(),
                savedStore.getClosedAt(),
                savedStore.getCreatedAt(),
                savedStore.getUpdatedAt()
        );

        return new CommonResDto<>("가게 생성 완료", responseOwnerStoreDto);

    }

    public CommonListResDto<ResponseOwnerStoreDto> viewAllStore(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        List<Store> stores = storeRepository.findByUserIdAndStatusNot(user.getId(), StoreStatus.CLOSED);

        List<ResponseOwnerStoreDto> storeDtos = new ArrayList<>();

        for (Store store : stores) {
            ResponseOwnerStoreDto dto = new ResponseOwnerStoreDto(
                    store.getId(),
                    store.getName(),
                    store.getMinPrice(),
                    store.getStatus().name(),
                    store.getOpenedAt(),
                    store.getClosedAt(),
                    store.getCreatedAt(),
                    store.getUpdatedAt()
            );
            storeDtos.add(dto);
        }

        return new CommonListResDto<>("가게 조회 완료", storeDtos);
    }

    public CommonListResDto<ResponseOwnerStoreDto> viewOneStore(Long id, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        Store store = ownerStoreRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new StoreNotFoundException("가게를 찾을 수 없습니다."));

        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreNotFoundException("폐업된 가게입니다.");
        }

        ResponseOwnerStoreDto storeDto = new ResponseOwnerStoreDto(
                store.getId(),
                store.getName(),
                store.getMinPrice(),
                store.getStatus().name(),
                store.getOpenedAt(),
                store.getClosedAt(),
                store.getCreatedAt(),
                store.getUpdatedAt()
        );

        return new CommonListResDto<>("가게 조회 완료", List.of(storeDto));
    }

    public CommonListResDto<ResponseMenuDto> viewMenus(Long id, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        Store store = ownerStoreRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new StoreNotFoundException("가게를 찾을 수 없습니다."));

        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreNotFoundException("폐업된 가게입니다.");
        }

        List<Menu> menus = menuRepository.findByStoreId(store.getId());

        List<ResponseMenuDto> menuDtos = new ArrayList<>();

        for (Menu menu : menus) {
            ResponseMenuDto menuDto = new ResponseMenuDto(menu);
            menuDtos.add(menuDto);
        }

        return new CommonListResDto<>("메뉴 조회 완료", menuDtos);
    }

    public CommonResDto<ResponseOwnerStoreUpdateDto> updateStore(Long id, RequestOwnerStoreUpdateDto requestOwnerStoreUpdateDto, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        Store store = ownerStoreRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new StoreNotFoundException("가게를 찾을 수 없습니다."));

        if (requestOwnerStoreUpdateDto.getName() != null && !requestOwnerStoreUpdateDto.getName().trim().isEmpty()) {
            store.setName(requestOwnerStoreUpdateDto.getName());
        }

        if (requestOwnerStoreUpdateDto.getMinPrice() > 0) {
            store.setMinPrice(requestOwnerStoreUpdateDto.getMinPrice());
        }

        if (requestOwnerStoreUpdateDto.getOpenedAt() != null) {
            store.setOpenedAt(requestOwnerStoreUpdateDto.getOpenedAt());
        }

        if (requestOwnerStoreUpdateDto.getClosedAt() != null) {
            store.setClosedAt(requestOwnerStoreUpdateDto.getClosedAt());
        }

        if (requestOwnerStoreUpdateDto.getStatus() != null) {
            store.setStatus(requestOwnerStoreUpdateDto.getStatus());
        }

        Store updatedStore = ownerStoreRepository.save(store);

        ResponseOwnerStoreUpdateDto responseStoreDto = new ResponseOwnerStoreUpdateDto(
                updatedStore.getId(),
                updatedStore.getName(),
                updatedStore.getMinPrice(),
                updatedStore.getOpenedAt(),
                updatedStore.getClosedAt()
        );

        return new CommonResDto<>("가게 수정 완료", responseStoreDto);
    }

    public CommonResDto<ResponseOrderUpdateDto> updateOrder(Long storeId, Long orderId, RequestOrderUpdateDto requestOrderUpdateDto, HttpServletRequest request) {

        Order order = (Order) orderRepository.findByStoreIdAndId(storeId, orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));

        order.updateStatus(requestOrderUpdateDto.getStatus());

        orderRepository.save(order);

        int totalPrice = order.getOrderMenus().stream()
                .mapToInt(orderMenu -> orderMenu.getMenu().getPrice() * orderMenu.getAmount())
                .sum();

        ResponseOrderUpdateDto responseDto = new ResponseOrderUpdateDto(
                order.getId(),
                order.getStatus(),
                totalPrice,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );

        return new CommonResDto<>("해당 주문의 상태가 변경 되었습니다.", responseDto);
    }

    public CommonListResDto<ResponseViewOrderDto> viewStoreOrders(Long id, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        Store store = ownerStoreRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new StoreNotFoundException("가게를 찾을 수 없습니다."));

        if (store.getStatus() == StoreStatus.CLOSED) {
            throw new StoreNotFoundException("폐업된 가게입니다.");
        }

        List<Order> orders = store.getOrders();

        orders.sort((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt()));

        List<ResponseViewOrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            String mainMenu = "";
            double maxPrice = 0.0;
            int totalMenuCount = order.getOrderMenus().size();
            for (OrderMenu orderMenu : order.getOrderMenus()) {
                double menuPrice = orderMenu.getMenu().getPrice();
                if (menuPrice > maxPrice) {
                    maxPrice = menuPrice;
                    mainMenu = orderMenu.getMenu().getName();
                }
            }
            ResponseViewOrderDto dto = new ResponseViewOrderDto(
                    order.getId(),
                    order.getStatus(),
                    order.getUser().getAddress(),
                    mainMenu,
                    totalMenuCount
            );
            orderDtos.add(dto);
        }

        return new CommonListResDto<>("주문내역 조회 완료", orderDtos);

    }

}