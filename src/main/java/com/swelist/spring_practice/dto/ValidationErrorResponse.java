package com.swelist.spring_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;
@Getter
@Builder
@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;
    private int status;
    private Instant timestamp;
    private Map<String, String> errors;
}
