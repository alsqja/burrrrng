package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.util.List;

@Getter
public class CartMenuListResDto implements ResDtoDataType {

    private final List<CartMenuResDto> cartMenus;

    public CartMenuListResDto(List<CartMenuResDto> cartMenus) {
        this.cartMenus = cartMenus;
    }
}
