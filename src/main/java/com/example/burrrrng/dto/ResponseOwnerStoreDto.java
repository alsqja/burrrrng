package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseOwnerStoreDto implements ResDtoDataType {
    private Long id;
    private String name;
    private int minPrice;
    private String status;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public ResponseOwnerStoreDto(Long id, String name, int minPrice, String status,
                                 LocalDateTime openedAt, LocalDateTime closedAt,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
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
