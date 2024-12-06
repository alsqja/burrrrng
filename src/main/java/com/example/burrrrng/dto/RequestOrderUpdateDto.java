package com.example.burrrrng.dto;

import com.example.burrrrng.enums.OrderStatus;
import lombok.Getter;

@Getter
public class RequestOrderUpdateDto {

    private OrderStatus status;

    public RequestOrderUpdateDto(){}

    public RequestOrderUpdateDto(OrderStatus status){
        this.status = status;
    }
}
