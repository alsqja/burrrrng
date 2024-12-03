package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResDto implements ResDtoDataType {

    private final Long id;
    private final Long orderId;
    private final Long userId;
    private final int star;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ReviewResDto(Long id, Long orderId, Long userId, int star, String comment, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.star = star;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ReviewResDto(Review review) {
        this.id = review.getId();
        this.orderId = review.getOrder().getId();
        this.userId = review.getUser().getId();
        this.star = review.getStar();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}
