package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ResponseOwnerStoreUpdateDto implements ResDtoDataType {

    private final Long id;
    private final String name;
    private final int minPrice;
    private final LocalTime openedAt;
    private final LocalTime closedAt;

    public ResponseOwnerStoreUpdateDto(Long id, String name, int minPrice, LocalTime openedAt, LocalTime closedAt) {
        this.id = id;
        this.name = name;
        this.minPrice = minPrice;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }
}
