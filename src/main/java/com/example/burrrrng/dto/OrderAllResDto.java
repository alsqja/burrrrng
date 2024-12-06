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

    public OrderAllResDto(Long id, String storeName, String status, String mainMenu, int totalMenuCount, int totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.storeName = storeName;
        this.status = status;
        this.mainMenu = mainMenu;
        this.totalMenuCount = totalMenuCount;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderAllResDto(Object[] result) {
        this.id = ((Number) result[0]).longValue(); // 첫 번째 컬럼: 주문 ID
        this.storeName = (String) result[1]; // 두 번째 컬럼: 상점 이름
        this.status = (String) result[2]; // 세 번째 컬럼: 상태
        this.mainMenu = (String) result[3]; // 네 번째 컬럼: 가장 비싼 메뉴
        this.totalMenuCount = ((Number) result[4]).intValue(); // 다섯 번째 컬럼: 메뉴 종류 수
        this.totalPrice = ((Number) result[5]).intValue(); // 여섯 번째 컬럼: 총 가격
        this.createdAt = convertToLocalDateTime(result[6]); // 일곱 번째 컬럼: 생성 시간
        this.updatedAt = convertToLocalDateTime(result[7]); // 여덟 번째 컬럼: 수정 시간
    }

    // Timestamp를 LocalDateTime으로 변환하는 헬퍼 메서드
    private LocalDateTime convertToLocalDateTime(Object timestamp) {
        if (timestamp instanceof Timestamp) {
            return ((Timestamp) timestamp).toLocalDateTime();
        }
        return null;
    }
}

