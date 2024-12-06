package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderMenuResDto implements ResDtoDataType {

    private final Long id;
    private final Long orderId;
    private final Long menuId;
    private final String menuName;
    private final int amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderMenuResDto(Long id, Long orderId, Long menuId, String menuName, int amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
