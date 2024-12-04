package com.example.burrrrng.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class RequestOwnerStoreDto {

    private String name;
    private int minPrice;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    public RequestOwnerStoreDto() {
    }

    public RequestOwnerStoreDto(String name, int minPrice, LocalDateTime openedAt, LocalDateTime closedAt) {
        this.name = name;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }
}
