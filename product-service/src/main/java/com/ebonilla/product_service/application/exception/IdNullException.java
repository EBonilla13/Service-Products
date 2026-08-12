package com.ebonilla.product_service.application.exception;

public class IdNullException extends RuntimeException{
    public IdNullException(){
        super("Id cannot be null");
    }
}
