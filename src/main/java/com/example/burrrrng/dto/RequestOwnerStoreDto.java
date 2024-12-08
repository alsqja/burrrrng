package com.example.burrrrng.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RequestOwnerStoreDto {

    private final String name;
    private final int minPrice;
    private final LocalTime openedAt;
    private final LocalTime closedAt;

    public RequestOwnerStoreDto(String name, int minPrice, LocalTime openedAt, LocalTime closedAt) {
        this.name = name;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }
}
