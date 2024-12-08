package com.example.burrrrng.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestMenuCreateDto {

    @NotBlank(message = "이름은 필수입니다.")
    private final String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 500, message = "가격은 최소 500원 이상이어야 합니다.")
    private final int price;

    public RequestMenuCreateDto(String name, int price) {
        this.name = name;
        this.price = price;
    }

}
