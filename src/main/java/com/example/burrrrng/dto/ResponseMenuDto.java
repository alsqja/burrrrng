package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.entity.Menu;
import lombok.Getter;

@Getter
public class ResponseMenuDto implements ResDtoDataType {
    private final Long id;
    private final String name;
    private final int price;
    private final String status;

    public ResponseMenuDto(Long id, String name, int price, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public ResponseMenuDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.status = menu.getStatus().getValue();
    }
}
