package com.example.burrrrng.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewReqDto {

    @NotNull(message = "별점을 입력해 주세요.")
    @Min(value = 1, message = "별점은 최소 1점 입니다.")
    @Max(value = 5, message = "별점은 최대 5점 입니다.")
    private final int star;

    @NotNull(message = "내용을 작성해 주세요.")
    private final String comment;

    public CreateReviewReqDto(int star, String comment) {
        this.star = star;
        this.comment = comment;
    }
}
