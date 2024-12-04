package com.example.burrrrng.service;

import com.example.burrrrng.config.CartCookieEncoder;
import com.example.burrrrng.dto.CartMenuListResDto;
import com.example.burrrrng.dto.CartMenuResDto;
import com.example.burrrrng.dto.CartReqDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.repository.MenuRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final MenuRepository menuRepository;
    private final CartCookieEncoder cartCookieEncoder;

    @Override
    public CartMenuListResDto save(HttpServletResponse response, CartReqDto dto) {

        List<Menu> menus = menuRepository.findAllById(dto.getMenus()).stream()
                .filter(i -> i.getStatus().getValue().equals("normal")).toList();

        if (menus.size() != dto.getMenus().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니 메뉴를 확인해 주세요.");
        }

        Long storeId = 0L;
        for (Menu menu : menus) {
            if (!storeId.equals(menu.getStore().getId()) && !storeId.equals(0L)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니에는 같은 가게의 상품만 담을 수 있습니다.");
            }
            storeId = menu.getStore().getId();
        }

        List<CartMenuResDto> cartMenus = menus.stream().map(i -> new CartMenuResDto(
                i.getId(),
                i.getName(),
                i.getPrice() * dto.getAmounts().get(menus.indexOf(i)),
                dto.getAmounts().get(menus.indexOf(i))
        )).toList();

        cartCookieEncoder.setCartToCookies(cartMenus, response);

        return new CartMenuListResDto(cartMenus);
    }
}
