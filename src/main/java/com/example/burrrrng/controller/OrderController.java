package com.example.burrrrng.controller;

import com.example.burrrrng.dto.OrderResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResDto<OrderResDto>> createOrder(
            HttpServletRequest request,
            HttpServletResponse response,
            @SessionAttribute User loginUser
    ) {

        return new ResponseEntity<>(new CommonResDto<>("주문 완료", orderService.save(request, response, loginUser.getId())), HttpStatus.CREATED);
    }
}
