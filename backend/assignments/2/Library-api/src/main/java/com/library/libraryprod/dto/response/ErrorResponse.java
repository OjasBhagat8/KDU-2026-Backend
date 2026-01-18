package com.library.libraryprod.dto.response;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ErrorResponse {

    private Instant timestamp;
    private String path;
    private String errorCode;
    private String message;
    private List<ErrorDetail> details;
    private String correlationId;

    public static class ErrorDetail {
        private String field;
        private String issue;
    }
}
