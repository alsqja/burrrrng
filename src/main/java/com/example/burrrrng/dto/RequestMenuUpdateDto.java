package com.example.burrrrng.dto;

import com.example.burrrrng.enums.MenuStatus;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class RequestMenuUpdateDto {


    private final String name;

    @Min(value = 500, message = "가격은 최소 500원 이상이어야 합니다.")
    private final int price;

    private final MenuStatus status;

    public RequestMenuUpdateDto(String name, int price, MenuStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }
}
