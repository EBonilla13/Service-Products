package com.ebonilla.product_service.infrastructure.adapters.input.rest.util;

import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.ErrorResponse;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.FieldResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponseUtil {

    public static ErrorResponse handlerMessageError(HttpStatus httpStatus, String message, String path){
        return new ErrorResponse(
                httpStatus.value(),
                httpStatus.toString(),
                message,
                path
        );
    }

    public static ErrorResponse handlerListErrors(HttpStatus httpStatus, List<FieldResponse> list, String path){
        return new ErrorResponse(
                httpStatus.value(),
                httpStatus.toString(),
                list,
                path
        );
    }

}
