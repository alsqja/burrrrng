package com.example.burrrrng.service;


import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonListResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.dto.RequestOwnerStoreDto;
import com.example.burrrrng.dto.ResponseOwnerStoreDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.StoreLimitException;
import com.example.burrrrng.exception.StoreNotFoundException;
import com.example.burrrrng.exception.UnauthorizedException;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OwnerStoreRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerStoreService {
    private final OwnerStoreRepository ownerStoreRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public CommonResDto<ResponseOwnerStoreDto> createStore(RequestOwnerStoreDto requestOwnerStoreDto, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);
        String name = requestOwnerStoreDto.getName();
        LocalTime openedAt = requestOwnerStoreDto.getOpenedAt();
        LocalTime closedAt = requestOwnerStoreDto.getClosedAt();
        int minPrice = requestOwnerStoreDto.getMinPrice();
        StoreStatus status = StoreStatus.OPENED;

        if (!user.getRole().equals(UserRole.OWNER)) {
            throw new UnauthorizedException("관리자만 작성 가능합니다.");
        }
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

        List<Store> stores = storeRepository.findByUserId(user.getId());

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

        List<Menu> menus = menuRepository.findByStoreId(store.getId());

        List<ResponseMenuDto> menuDtos = new ArrayList<>();

        for (Menu menu : menus) {
            ResponseMenuDto menuDto = new ResponseMenuDto(menu);
            menuDtos.add(menuDto);
        }
        return new CommonListResDto<>("메뉴 조회 완료",menuDtos);
    }
}