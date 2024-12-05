package com.example.burrrrng.dto;

import com.example.burrrrng.dto.common.ResDtoDataType;
import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.enums.MenuStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuResDto implements ResDtoDataType {

    private final Long id;
    private final String name;
    private final int price;
    private final MenuStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public MenuResDto(Long id, String name, int price, MenuStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public MenuResDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
        this.createdAt = menu.getCreatedAt();
        this.updatedAt = menu.getUpdatedAt();
    }
}
