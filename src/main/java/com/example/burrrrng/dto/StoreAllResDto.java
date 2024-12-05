package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.entity.Store;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreAllResDto implements ResDtoDataType {

    private final Long id;
    private final String name;
    private final int minPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public StoreAllResDto(Long id, String name, int minPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.minPrice = minPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public StoreAllResDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.minPrice = store.getMinPrice();
        this.createdAt = store.getCreatedAt();
        this.updatedAt = store.getUpdatedAt();
    }
}
