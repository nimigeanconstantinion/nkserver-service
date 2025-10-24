package com.example.nkserver.error;

import java.util.Map;

public record ApiErrorResponse(
        String title,
        int status,
        Map<String, Object> properties
) {
    public static ApiErrorResponse of(String title, int status) {
        return new ApiErrorResponse(title, status, Map.of());
    }

    public static ApiErrorResponse withErrors(String title, int status, Map<String, String> errors) {
        return new ApiErrorResponse(title, status, Map.of("errors", errors));
    }

    public static ApiErrorResponse withProperties(String title, int status, Map<String, Object> properties) {
        return new ApiErrorResponse(title, status, properties);
    }
}
