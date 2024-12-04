package com.example.burrrrng.config;

import com.example.burrrrng.common.Const;
import com.example.burrrrng.dto.CartMenuResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
        }

    }

    public List<CartMenuResDto> getCartFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Const.CART_COOKIE_NAME.equals(cookie.getName())) {
                    try {
                        // URL 디코딩
                        String decodedCartJson = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        // JSON을 객체로 변환
                        return objectMapper.readValue(decodedCartJson, List.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ArrayList<>();
    }

}
