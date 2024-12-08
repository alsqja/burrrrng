package com.example.burrrrng.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CartReqDto {

    private final List<Long> menus;
    private final List<Integer> amounts;

    public CartReqDto(List<Long> menus, List<Integer> amounts) {
        this.menus = menus;
        this.amounts = amounts;
    }
}
