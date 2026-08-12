package com.ebonilla.product_service.infrastructure.adapters.input.rest.handler;

import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.ErrorResponse;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.FieldResponse;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.util.ErrorResponseUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Manejo de validaciones en DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleValidationException(
            MethodArgumentNotValidException exception, WebRequest webRequest){

        List<FieldResponse> details = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.badRequest().body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerListErrors(
                                HttpStatus.BAD_REQUEST, details, webRequest.getDescription(false)
                        )));
    }

    // Manejador de validaciones de datos de controladores
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handlerMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest webRequest){

        String msg = String.format("Value '%s' is invalid, field '%s' is required", ex.getValue(), ex.getName());

        return ResponseEntity.badRequest().body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerMessageError(
                                HttpStatus.BAD_REQUEST, msg, webRequest.getDescription(false)
                        )
                )
        );
    }

    // Manejador de validaciones de parametros de controladores (anotacion @VALIDATED)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handlerConstraintViolation(
            ConstraintViolationException ex, WebRequest webRequest){

        List<FieldResponse> details = ex.getConstraintViolations()
                .stream()
                .map(FieldResponse::new)
                .toList();

        return ResponseEntity.badRequest().body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerListErrors(
                                HttpStatus.BAD_REQUEST,
                                details,
                                webRequest.getDescription(false))
                )
        );
    }

    // Manejador de validacion de anotacion @RequestParam
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handlerMissingServletRequestParameter(
            MissingServletRequestParameterException ex, WebRequest webRequest){

        String msg = String.format("%s : %s", ex.getParameterName(), ex.getMessage());

        return ResponseEntity.badRequest().body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerMessageError(
                                HttpStatus.BAD_REQUEST,
                                msg,
                                webRequest.getDescription(false)
                        )
                )
        );
    }


    // Validacion de dominio
    @ExceptionHandler(IdNullException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleIdNullException(
            IdNullException exception, WebRequest webRequest){

        return ResponseEntity.badRequest().body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerMessageError(
                                HttpStatus.BAD_REQUEST, exception.getMessage(), webRequest.getDescription(false)
                        )));
    }

    // Datos no existentes en DB
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleResourcenotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerMessageError(
                                HttpStatus.NOT_FOUND, exception.getMessage(), webRequest.getDescription(false)
                        )));
    }

    // Validaciones de modelos de dominio
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<BaseResponse<ErrorResponse>> handleBusinessLogicException(
            BusinessLogicException exception, WebRequest webRequest){

        List<FieldResponse> details = new ArrayList<>();
        int count = 1;

        for (String msg : exception.getValidations()){
            FieldResponse response = new FieldResponse();
            response.setField("[" + count + "]");
            response.setMessage(msg);
            details.add(response);
            count++;
        }

        return ResponseEntity.badRequest().body(
                BaseResponse.error(
                        ErrorResponseUtil.handlerListErrors(
                                HttpStatus.BAD_REQUEST, details, webRequest.getDescription(false)
                        )
                )
        );
    }
}
