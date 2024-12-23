package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreResDto implements ResDtoDataType {

    private final Long id;
    private final String name;
    private final double star;
    private final int minPrice;
    private final LocalTime openedAt;
    private final LocalTime closedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public StoreResDto(Long id, String name, double star, int minPrice, LocalTime openedAt, LocalTime closedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
