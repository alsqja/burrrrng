package com.example.burrrrng.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class RequestOwnerStoreDto {

    private String name;
    private int minPrice;
    private LocalTime openedAt;
    private LocalTime closedAt;

    public RequestOwnerStoreDto() {
    }

    public RequestOwnerStoreDto(String name, int minPrice, LocalTime openedAt, LocalTime closedAt) {
        this.name = name;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }
}
