package com.ebonilla.product_service.application.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessLogicException extends RuntimeException {

    private final List<String> validations;

    public BusinessLogicException(List<String> validations){
        super();
        this.validations = validations;
    }

}
