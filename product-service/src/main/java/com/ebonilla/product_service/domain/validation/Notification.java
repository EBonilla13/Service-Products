package com.ebonilla.product_service.domain.validation;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    // Esta clase nos ayuda a almacenar los errores de validacion de las entidades.
    // Nos permite gestionar mas de 1 error de validacion de las entidades
    private final List<String> errors = new ArrayList<>();

    public Notification(){}

    public void addError(String error){
        this.errors.add(error);
    }

    public Boolean hasErrors(){
        return !this.errors.isEmpty();
    }

    public List<String> getErrors(){
        return List.copyOf(this.errors);
    }
}
