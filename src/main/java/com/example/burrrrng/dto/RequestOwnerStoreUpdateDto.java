package com.example.burrrrng.dto;

import com.example.burrrrng.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RequestOwnerStoreUpdateDto {

    private Long storeId;
    private String name;
    private int minPrice;
    private LocalTime openedAt;
    private LocalTime closedAt;
    private StoreStatus status;

    public RequestOwnerStoreUpdateDto() {
    }

    public RequestOwnerStoreUpdateDto(Long storeId, String name, int minPrice, LocalTime openedAt, LocalTime closedAt, StoreStatus status) {
        this.storeId = storeId;
        this.name = name;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.status = status;
    }
}
