package com.example.burrrrng.service;

import com.example.burrrrng.dto.OrderAllResDto;
import com.example.burrrrng.dto.OrderResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface OrderService {
    OrderResDto save(HttpServletRequest request, HttpServletResponse response, Long id);

    List<OrderAllResDto> findAllUserOrder(Long id);
}
