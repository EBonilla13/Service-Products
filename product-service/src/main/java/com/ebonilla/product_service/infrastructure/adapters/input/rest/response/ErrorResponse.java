package com.ebonilla.product_service.infrastructure.adapters.input.rest.response;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ErrorResponse {
    private final Integer status;
    private final String error;
    private final String timeStamp;
    private final String message;
    private final List<FieldResponse> details;
    private final String path;

    public ErrorResponse(Integer status, String error, List<FieldResponse> details, String path) {
        this.status = status;
        this.error = error;
        this.timeStamp = dateToString();
        this.message = null;
        this.details = details;
        this.path = path;
    }

    public ErrorResponse(Integer status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.timeStamp = dateToString();
        this.message = message;
        this.details = null;
        this.path = path;
    }

    // Metodo para transformar la fecha a String
    private String dateToString(){
        LocalDateTime currently = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currently.format(formatter);
    }
}
