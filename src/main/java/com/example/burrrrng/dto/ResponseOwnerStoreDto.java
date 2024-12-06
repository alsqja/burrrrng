package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ResponseOwnerStoreDto implements ResDtoDataType {

    private final Long id;
    private final String name;
    private final int minPrice;
    private final String status;
    private final LocalTime openedAt;
    private final LocalTime closedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public ResponseOwnerStoreDto(
            Long id,
            String name,
            int minPrice,
            String status,
            LocalTime openedAt,
            LocalTime closedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.minPrice = minPrice;
        this.status = status;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
