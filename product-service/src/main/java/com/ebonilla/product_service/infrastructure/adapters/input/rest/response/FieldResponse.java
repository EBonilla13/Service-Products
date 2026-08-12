package com.ebonilla.product_service.infrastructure.adapters.input.rest.response;

import jakarta.validation.ConstraintViolation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FieldResponse {
    private String field;
    private String message;

    public FieldResponse(ConstraintViolation<?> cv){
        String path = cv.getPropertyPath().toString();
        this.field = path.contains(".") ? path.substring(path.lastIndexOf(".") + 1) : path;
        this.message = cv.getMessage();
    }
}
