package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.enums.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseOrderUpdateDto implements ResDtoDataType {

    private final Long id;
    private final OrderStatus status;
    private final int totalPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ResponseOrderUpdateDto(Long id, OrderStatus status, int totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
