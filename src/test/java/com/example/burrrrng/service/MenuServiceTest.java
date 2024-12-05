package com.example.burrrrng.service;

import com.example.burrrrng.dto.RequestMenuCreateDto;
import com.example.burrrrng.dto.RequestMenuUpdateDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.enums.StoreStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.SameMenuException;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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
}
