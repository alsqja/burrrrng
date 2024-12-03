package com.example.burrrrng.service;

import com.example.burrrrng.dto.CreateReviewReqDto;
import com.example.burrrrng.dto.ReviewResDto;

public interface ReviewService {

    ReviewResDto save(Long orderId, Long userId, CreateReviewReqDto dto);
}
