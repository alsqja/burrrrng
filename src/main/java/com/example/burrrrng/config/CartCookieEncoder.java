package com.example.burrrrng.config;

import com.example.burrrrng.constants.Const;
import com.example.burrrrng.dto.CartMenuResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartCookieEncoder {
    private final ObjectMapper objectMapper;

    public void setCartToCookies(List<CartMenuResDto> cartItems, HttpServletResponse response) {

        try {
            String cartJson = objectMapper.writeValueAsString(cartItems);

            String encodedCartJson = URLEncoder.encode(cartJson, "UTF-8");

            Cookie cookie = new Cookie(Const.CART_COOKIE_NAME, encodedCartJson);
            cookie.setMaxAge(Const.ONE_DAY_SECONDS);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "장바구니 데이터를 처리하는 데 오류가 발생했습니다.");
        }

    }

    public List<CartMenuResDto> getCartFromCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니에 상품을 담아주세요.");
        }

        for (Cookie cookie : cookies) {
            if (Const.CART_COOKIE_NAME.equals(cookie.getName())) {
                try {

                    String decodedCartJson = URLDecoder.decode(cookie.getValue(), "UTF-8");

                    return objectMapper.readValue(decodedCartJson, new TypeReference<List<CartMenuResDto>>() {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "장바구니 데이터를 처리하는 데 오류가 발생했습니다.");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니에 상품을 담아주세요.");
    }

    public void deleteCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(Const.CART_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
