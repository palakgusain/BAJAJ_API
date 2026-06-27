package com.chitkara.bfhl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postBfhlReturns200ForValidRequest() throws Exception {
        String body = objectMapper.writeValueAsString(
                java.util.Map.of("data", java.util.List.of("a", "1", "334", "4", "R", "$")));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("john_doe_17091999"))
                .andExpect(jsonPath("$.email").value("john@xyz.com"))
                .andExpect(jsonPath("$.roll_number").value("ABCD123"))
                .andExpect(jsonPath("$.odd_numbers", contains("1")))
                .andExpect(jsonPath("$.even_numbers", contains("334", "4")))
                .andExpect(jsonPath("$.alphabets", contains("A", "R")))
                .andExpect(jsonPath("$.special_characters", contains("$")))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.concat_string").value("Ra"));
    }

    @Test
    void postBfhlReturns400WhenDataMissing() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.message").value("data field is required and cannot be null"));
    }

    @Test
    void postBfhlReturns400ForMalformedJson() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.message").value("Malformed JSON request body"));
    }

    @Test
    void getBfhlReturns405() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.message").value("Only POST method is supported for /bfhl"));
    }

    @Test
    void postBfhlReturns415ForNonJsonContentType() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("data"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.is_success").value(false));
    }
}
