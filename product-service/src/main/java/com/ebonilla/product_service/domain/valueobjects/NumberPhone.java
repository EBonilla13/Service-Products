package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode
public final class NumberPhone {

    private final String numberPhone;

    private NumberPhone(String numberPhone){
        this.numberPhone = numberPhone;
    }

    public static Optional<NumberPhone> create(String numberPhone, String reference,
                                               Notification notification){
        // Se usa la clase Pattern y Matcher para evaluar el numero telefonico (Solo numeros y tamanio de 10 o 12)
        if (Pattern.matches("\\d{10}", numberPhone) || Pattern.matches("\\d{12}", numberPhone)) {
            return Optional.of(new NumberPhone(numberPhone));
        }

        notification.addError("Invalid " + reference + " number phone, can be between 10 and 12 digits");
        return Optional.empty();
    }
}
