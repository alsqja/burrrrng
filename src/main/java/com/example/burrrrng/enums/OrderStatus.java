package com.example.burrrrng.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum OrderStatus {
    UNCHECKED("unchecked"),
    CHECKED("checked"),
    COOKING("cooking"),
    COOKED("cooked"),
    RIDING("riding"),
    COMPLETE("complete");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus state : OrderStatus.values()) {
            if (state.value.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문 status 를 확인해주세요.");
    }
}
