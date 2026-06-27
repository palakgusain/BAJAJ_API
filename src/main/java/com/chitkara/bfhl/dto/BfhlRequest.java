package com.chitkara.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BfhlRequest {

    @NotNull(message = "data field is required and cannot be null")
    @JsonProperty("data")
    private List<String> data;

    public BfhlRequest() {
    }

    public BfhlRequest(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
