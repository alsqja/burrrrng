package com.example.burrrrng.controller;

import com.example.burrrrng.dto.CartMenuListResDto;
import com.example.burrrrng.dto.CartReqDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.service.CartService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CommonResDto<CartMenuListResDto>> createCart(
            @RequestBody CartReqDto dto,
            HttpServletResponse response
    ) {

        if (dto.getAmounts().size() != dto.getMenus().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문 메뉴와 수량을 확인해 주세요.");
        }

        return new ResponseEntity<>(new CommonResDto<>("카트 담기 완료", cartService.save(response, dto)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResDto<CartMenuListResDto>> updateCart(
            @RequestBody CartReqDto dto,
            HttpServletResponse response
    ) {
        if (dto.getAmounts().size() != dto.getMenus().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문 메뉴와 수량을 확인해 주세요.");
        }

        return new ResponseEntity<>(new CommonResDto<>("카트 수정 완료", cartService.save(response, dto)), HttpStatus.OK);
    }
}
