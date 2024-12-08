package com.example.burrrrng.dto;

import com.example.burrrrng.enums.OrderStatus;
import lombok.Getter;

@Getter
public class RequestOrderUpdateDto {

    private final OrderStatus status;

    public RequestOrderUpdateDto(OrderStatus status) {
        this.status = status;
    }
}
