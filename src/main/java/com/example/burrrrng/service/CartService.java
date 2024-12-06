package com.example.burrrrng.service;

import com.example.burrrrng.dto.CartMenuListResDto;
import com.example.burrrrng.dto.CartReqDto;
import jakarta.servlet.http.HttpServletResponse;

public interface CartService {
    
    CartMenuListResDto save(HttpServletResponse response, CartReqDto dto);
}
