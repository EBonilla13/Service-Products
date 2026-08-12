package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@ToString
public final class Email {

    private final String email;
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9-._]+@[a-zA-Z0-9]+(?:\\.[a-zA-Z]+)+$";

    private Email(String email) {
        this.email = email;
    }

    public static Optional<Email> create(String email, int maxLength,
                                  String reference, Notification notification){
        if (email != null){
            if (email.length() > maxLength) {
                notification.addError(reference + " must have a maximum length of " + maxLength + " characters");
                return Optional.empty();
            }else if (!Pattern.matches(REGEX_EMAIL, email)){
                notification.addError(reference + " is invalid");
                return Optional.empty();
            }
        }
        return Optional.of(new Email(email));
    }
}
