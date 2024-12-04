package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

@Getter
public class CartMenuResDto implements ResDtoDataType {

    private final Long id;
    private final String name;
    private final int price;
    private final int amount;

    public CartMenuResDto(Long id, String name, int price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
