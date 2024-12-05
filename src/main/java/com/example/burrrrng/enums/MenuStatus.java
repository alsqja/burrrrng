package com.example.burrrrng.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum MenuStatus {
    NORMAL("normal"),
    SOLDOUT("soldout");

    private final String value;

    MenuStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MenuStatus fromValue(String value) {
        for (MenuStatus state : MenuStatus.values()) {
            if (state.value.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "메뉴 status 를 확인해주세요.");
    }
}
