package com.example.burrrrng.service;

import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.RequestOwnerStoreDto;
import com.example.burrrrng.dto.ResponseOwnerStoreDto;
import com.example.burrrrng.dto.StoreAllResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.StoreLimitException;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class StoreServiceTest {

    @Autowired
    StoreService storeService;
    @Autowired
    OwnerStoreService ownerStoreService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 가게생성_예외() {
        RequestOwnerStoreDto dto = new RequestOwnerStoreDto("testStoreName", 12000, LocalTime.of(10, 0), LocalTime.of(22, 0));

        User user = userRepository.save(new User("test@email.com", "0000", "testUserName", "testUserAddress", UserRole.OWNER));

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(Const.LOGIN_USER, user);
        request.setSession(session);

        CommonResDto<ResponseOwnerStoreDto> saveStore = ownerStoreService.createStore(dto, request);
        CommonResDto<ResponseOwnerStoreDto> saveStore1 = ownerStoreService.createStore(dto, request);
        CommonResDto<ResponseOwnerStoreDto> saveStore2 = ownerStoreService.createStore(dto, request);

        Store findStore = storeRepository.findByIdOrElseThrow(saveStore.getData().getId());

        StoreLimitException e = assertThrows(StoreLimitException.class, () -> ownerStoreService.createStore(dto, request));

        assertThat(saveStore.getData().getName()).isEqualTo("testStoreName");
        assertThat(e.getMessage()).isEqualTo("폐업 상태가 아닌 가게를 3개 이상 운영할 수 없습니다.");
    }

    @Test
    void 가게선택조회() {
        User user = userRepository.save(new User("test@email.com", "0000", "testUserName", "testUserAddress", UserRole.OWNER));
        Store store = new Store(user, "testStoreName", LocalTime.of(10, 0), LocalTime.of(22, 0), 12000, StoreStatus.OPENED);

        Store savedStore = storeRepository.save(store);
        StoreAllResDto findStore = storeService.findById(savedStore.getId());

        assertThat(findStore.getName()).isEqualTo(savedStore.getName());
    }
}
