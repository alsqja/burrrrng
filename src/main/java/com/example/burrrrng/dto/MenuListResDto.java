package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuListResDto implements ResDtoDataType {

    private final List<MenuResDto> menus;

    public MenuListResDto(List<MenuResDto> menus) {
        this.menus = menus;
    }
}
