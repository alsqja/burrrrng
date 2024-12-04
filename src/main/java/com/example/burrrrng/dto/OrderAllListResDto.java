package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderAllListResDto implements ResDtoDataType {

    private final List<OrderAllResDto> orders;

    public OrderAllListResDto(List<OrderAllResDto> orders) {
        this.orders = orders;
    }
}
