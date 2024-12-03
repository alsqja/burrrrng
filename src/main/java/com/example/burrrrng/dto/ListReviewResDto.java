package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.util.List;

@Getter
public class ListReviewResDto implements ResDtoDataType {

    private final List<ReviewResDto> reviews;

    public ListReviewResDto(List<ReviewResDto> reviews) {
        this.reviews = reviews;
    }
}
