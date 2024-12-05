package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.entity.Menu;
import lombok.Getter;

@Getter
public class ResponseMenuDto implements ResDtoDataType {
    private Long id;
    private String name;
    private int price;
    private String status;

    public ResponseMenuDto() {}

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
