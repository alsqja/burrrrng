package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class OrderAllResDto implements ResDtoDataType {

    private final Long id;
    private final String storeName;
    private final String status;
    private final String mainMenu;
    private final int totalMenuCount;
    private final int totalPrice;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderAllResDto(Object[] result) {
        this.id = ((Number) result[0]).longValue();
        this.storeName = (String) result[1];
        this.status = (String) result[2];
        this.mainMenu = (String) result[3];
        this.totalMenuCount = ((Number) result[4]).intValue();
        this.totalPrice = ((Number) result[5]).intValue();
        this.createdAt = convertToLocalDateTime(result[6]);
        this.updatedAt = convertToLocalDateTime(result[7]);
    }

    private LocalDateTime convertToLocalDateTime(Object timestamp) {
        if (timestamp instanceof Timestamp) {
            return ((Timestamp) timestamp).toLocalDateTime();
        }
        return null;
    }
}

