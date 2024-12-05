package com.example.burrrrng.service;


import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.RequestMenuCreateDto;
import com.example.burrrrng.dto.RequestMenuUpdateDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.exception.*;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OwnerStoreRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final OwnerStoreRepository ownerStoreRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public CommonResDto<ResponseMenuDto> createMenu(Long id, RequestMenuCreateDto requestMenuCreateDto, HttpServletRequest request) {
        Store store = storeRepository.findById(id).orElse(null); // -> 가게 지정
        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        if (requestMenuCreateDto.getName() == null || requestMenuCreateDto.getName().trim().isEmpty()) {
            throw new NameAndPriceException("메뉴 이름은 필수 입력 사항입니다.");
        }

        if (requestMenuCreateDto.getPrice() == 0) {
            throw new NameAndPriceException("메뉴 가격은 필수 입력 사항입니다.");
        }

        if(!Objects.equals(store.getUser().getId(), user.getId())){
            throw new UnauthorizedException("본인 가게의 메뉴만 작성 가능합니다");
        }
        menuRepository.findByStoreAndName(store, requestMenuCreateDto.getName()).ifPresent(menu -> {
            throw new SameMenuException("같은 이름의 메뉴가 이미 존재합니다.");
        });

        MenuStatus status = MenuStatus.NORMAL;
        Menu newMenu = new Menu(user, store, requestMenuCreateDto.getName(), requestMenuCreateDto.getPrice(), status );

        Menu savedMenu = menuRepository.save(newMenu);

        ResponseMenuDto responseMenuDto = new ResponseMenuDto(
                newMenu.getId(),
                newMenu.getName(),
                newMenu.getPrice(),
                newMenu.getStatus().name()
        );
        return new CommonResDto<>("메뉴생성완료", responseMenuDto);
    }

    public CommonResDto<ResponseMenuDto> updateMenu(Long storeId, Long menuId, RequestMenuUpdateDto requestMenuUpdateDto, HttpServletRequest request) {
        Store store = storeRepository.findById(storeId).orElse(null);
        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);

        if(store == null){
            throw new StoreNotFoundException("가게가 존재하지 않습니다.");
        }
        Menu menu = menuRepository.findByStoreAndId(store, menuId).orElse(null);

        if(menu == null){
            throw new MenuNotFoundException("수정할 메뉴가 없습니다.");
        }
        List<Menu> menus = menuRepository.findByStoreId(storeId);
        for(Menu m : menus){
            if(m.getName().equals(requestMenuUpdateDto.getName())){
                throw new SameMenuException("같은 이름을 가진 메뉴가 이미 있습니다.");
            }
        }
        if (requestMenuUpdateDto.getName() != null && !requestMenuUpdateDto.getName().trim().isEmpty()) {
            menu.setName(requestMenuUpdateDto.getName());
        }

        if (requestMenuUpdateDto.getPrice() != 0) {
            menu.setPrice(requestMenuUpdateDto.getPrice());
        }

        if (requestMenuUpdateDto.getStatus() != null) {
            menu.setStatus(requestMenuUpdateDto.getStatus());
        }

        Menu updateMenu = menuRepository.save(menu);

        ResponseMenuDto responseMenuDto = new ResponseMenuDto(
                updateMenu.getId(),
                updateMenu.getName(),
                updateMenu.getPrice(),
                updateMenu.getStatus().name()
        );
        return new CommonResDto<>("메뉴 수정 완료", responseMenuDto);



    }

}
