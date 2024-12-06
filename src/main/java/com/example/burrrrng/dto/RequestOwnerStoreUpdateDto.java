package com.example.burrrrng.dto;

import com.example.burrrrng.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RequestOwnerStoreUpdateDto {

    private final Long storeId;
    private final String name;
    private final int minPrice;
    private final LocalTime openedAt;
    private final LocalTime closedAt;
    private final StoreStatus status;

    public RequestOwnerStoreUpdateDto(Long storeId, String name, int minPrice, LocalTime openedAt, LocalTime closedAt, StoreStatus status) {
        this.storeId = storeId;
        this.name = name;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.status = status;
    }
}
