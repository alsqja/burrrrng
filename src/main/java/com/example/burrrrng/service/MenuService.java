package com.example.burrrrng.service;


import com.example.burrrrng.dto.RequestMenuDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.MenuStatus;
import com.example.burrrrng.enums.UserRole;
import com.example.burrrrng.exception.NameAndPriceException;
import com.example.burrrrng.exception.SameMenuException;
import com.example.burrrrng.exception.UnauthorizedException;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.OwnerStoreRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final OwnerStoreRepository ownerStoreRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public CommonResDto<ResponseMenuDto> createMenu(Long id, RequestMenuDto requestMenuDto, HttpServletRequest request) {
        Store store = storeRepository.findById(id).orElse(null); // -> 가게 지정
        Long userId = 1L;
        User user = userRepository.findByIdOrElseThrow(userId); // -> 세션으로 유저 데이터 받기

        if (requestMenuDto.getName() == null || requestMenuDto.getName().trim().isEmpty()) {
            throw new NameAndPriceException("메뉴 이름은 필수 입력 사항입니다.");
        }

        if (requestMenuDto.getPrice() == 0) {
            throw new NameAndPriceException("메뉴 가격은 필수 입력 사항입니다.");
        }

        if (!user.getRole().equals(UserRole.OWNER)) {
            throw new UnauthorizedException("관리자만 작성 가능합니다.");
        }
        if(store.getUser() != user){
            throw new UnauthorizedException("본인 가게의 메뉴만 작성 가능합니다");
        }
        menuRepository.findByStoreAndName(store, requestMenuDto.getName()).ifPresent(menu -> {
            throw new SameMenuException("같은 이름의 메뉴가 이미 존재합니다.");
        });

        MenuStatus status = MenuStatus.NORMAL;
        Menu newMenu = new Menu(user, store, requestMenuDto.getName(), requestMenuDto.getPrice(), status );

        Menu savedMenu = menuRepository.save(newMenu);

        ResponseMenuDto responseMenuDto = new ResponseMenuDto(
                newMenu.getId(),
                newMenu.getName(),
                newMenu.getPrice(),
                newMenu.getStatus().name()
        );
        return new CommonResDto<>("메뉴생성완료",responseMenuDto);
    }

}
