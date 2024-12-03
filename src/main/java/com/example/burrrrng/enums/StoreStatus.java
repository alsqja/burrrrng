package com.example.burrrrng.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum StoreStatus {
    OPENED("opened"),
    CLOSED("closed");

    private final String value;

    StoreStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StoreStatus fromValue(String value) {
        for (StoreStatus state : StoreStatus.values()) {
            if (state.value.equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게 status 를 확인해주세요.");
    }
}
