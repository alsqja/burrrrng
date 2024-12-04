package com.example.burrrrng.service;


import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.dto.RequestOwnerStoreDto;
import com.example.burrrrng.dto.ResponseOwnerStoreDto;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.StoreLimitException;
import com.example.burrrrng.exception.UnauthorizedException;
import com.example.burrrrng.repository.OwnerStoreRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OwnerStoreService {
    private final OwnerStoreRepository ownerStoreRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

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

}