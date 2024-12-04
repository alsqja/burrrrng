package com.example.burrrrng.controller;

import com.example.burrrrng.dto.CreateReviewReqDto;
import com.example.burrrrng.dto.ListReviewResDto;
import com.example.burrrrng.dto.ReviewResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/orders/{orderId}/reviews")
    public ResponseEntity<CommonResDto<ReviewResDto>> createReview(
            @PathVariable Long orderId,
            @Valid @RequestBody CreateReviewReqDto dto
    ) {
        //  TODO: login 구현 후 session 정보 추가
        ReviewResDto result = reviewService.save(orderId, 1L, dto);

        return new ResponseEntity<>(new CommonResDto<>("리뷰 작성 완료", result), HttpStatus.CREATED);
    }

    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<CommonResDto<ListReviewResDto>> findAllStoreReviews(
            @PathVariable Long storeId
    ) {
        ListReviewResDto result = reviewService.findAllStoreReviews(storeId);

        return new ResponseEntity<>(new CommonResDto<>("리뷰 받기 완료", result), HttpStatus.OK);
    }
}
