package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
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
}
