package com.swelist.spring_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private Instant timestamp;
}
