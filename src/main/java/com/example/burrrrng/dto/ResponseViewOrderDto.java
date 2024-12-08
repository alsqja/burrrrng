package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.enums.OrderStatus;
import lombok.Getter;

@Getter
public class ResponseViewOrderDto implements ResDtoDataType {

    private final Long id;
    private final OrderStatus orderStatus;
    private final String address;
    private final String mainMenu;
    private final int totalMenuCount;

    public ResponseViewOrderDto(Long id, OrderStatus orderStatus, String address, String mainMenu, int totalMenuCount) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.address = address;
        this.mainMenu = mainMenu;
        this.totalMenuCount = totalMenuCount;
    }

}
