package com.chitkara.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("is_success")
    private final boolean success = false;

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
