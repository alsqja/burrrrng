package com.example.burrrrng.dto;


import lombok.Getter;

@Getter
public class RequestMenuDto {

    private String name;
    private int price;

    public RequestMenuDto(){}

    public RequestMenuDto(String name, int price){
        this.name = name;
        this.price = price;
    }

}
