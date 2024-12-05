package com.example.burrrrng.dto;

import com.example.burrrrng.enums.MenuStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestMenuUpdateDto {


    private String name;


    @Min(value = 500, message = "가격은 최소 500원 이상이어야 합니다.")
    private int price;

    private MenuStatus status;

    public RequestMenuUpdateDto(){}

    public RequestMenuUpdateDto(String name, int price, String status) {
        this.name = name;
        this.price = price;
        this.status = MenuStatus.valueOf(status);
    }
}
