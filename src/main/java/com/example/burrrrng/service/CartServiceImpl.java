package com.example.burrrrng.service;

import com.example.burrrrng.common.Const;
import com.example.burrrrng.dto.CartMenuListResDto;
import com.example.burrrrng.dto.CartMenuResDto;
import com.example.burrrrng.dto.CartReqDto;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.repository.MenuRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ObjectMapper objectMapper;
    private final MenuRepository menuRepository;

    @Override
    public CartMenuListResDto save(HttpServletResponse response, CartReqDto dto) {

        List<Menu> menus = menuRepository.findAllById(dto.getMenus()).stream()
                .filter(i -> i.getStatus().getValue().equals("normal")).toList();

        if (menus.size() != dto.getMenus().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니 메뉴를 확인해 주세요.");
        }

        List<CartMenuResDto> cartMenus = menus.stream().map(i -> new CartMenuResDto(
                i.getId(),
                i.getName(),
                i.getPrice(),
                dto.getAmounts().get(menus.indexOf(i))
        )).toList();

        setCartToCookies(cartMenus, response);

        return new CartMenuListResDto(cartMenus);
    }

    private void setCartToCookies(List<CartMenuResDto> cartItems, HttpServletResponse response) {

        try {
            String cartJson = objectMapper.writeValueAsString(cartItems);

            String encodedCartJson = URLEncoder.encode(cartJson, "UTF-8");

            Cookie cookie = new Cookie(Const.CART_COOKIE_NAME, encodedCartJson);
            cookie.setMaxAge(Const.ONE_DAY_SECONDS);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
