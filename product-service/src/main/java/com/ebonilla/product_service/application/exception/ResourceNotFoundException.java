package com.ebonilla.product_service.application.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String reference){
        super("Resource with " + reference + " not found");
    }
}
