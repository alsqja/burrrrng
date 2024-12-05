package com.example.burrrrng.dto.common;

import lombok.Getter;
import java.util.List;

@Getter
public class CommonListResDto<T extends ResDtoDataType> {

    private final String message;
    private final List<T> data;

    public CommonListResDto(String message, List<T> data) {
        this.message = message;
        this.data = data;
    }
}
