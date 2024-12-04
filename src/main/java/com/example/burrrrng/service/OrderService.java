package com.example.burrrrng.service;

import com.example.burrrrng.dto.OrderResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OrderService {
    OrderResDto save(HttpServletRequest request, HttpServletResponse response, Long id);
}
