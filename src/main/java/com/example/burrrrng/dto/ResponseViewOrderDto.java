package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.enums.OrderStatus;
import lombok.Getter;

@Getter
public class ResponseViewOrderDto implements ResDtoDataType {

    private Long id;
    private OrderStatus orderStatus;
    private String address;
    private String mainMenu;

    public ResponseViewOrderDto() {}

    public ResponseViewOrderDto(Long id, OrderStatus orderStatus, String address, String mainMenu) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.address = address;
        this.mainMenu = mainMenu;
    }

}
