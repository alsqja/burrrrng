package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreListResDto implements ResDtoDataType {

    private final List<StoreResDto> stores;

    public StoreListResDto(List<StoreResDto> stores) {
        this.stores = stores;
    }
}
