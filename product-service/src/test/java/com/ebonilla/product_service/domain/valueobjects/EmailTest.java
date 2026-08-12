package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private int maxLength;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    void shouldReturnValidEmail(){
        // 1. Arrange
        maxLength = 100;
        String email = "repoer_13_96@hotmail.com";

        // 2. Act
        Optional<Email> validEmail = Email.create(email, maxLength, "This email", notification);

        // 3. Assert
        System.out.println(validEmail.isEmpty() ? "Sin datos" : validEmail.get().toString());
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(validEmail.isPresent())
        );
    }

    @Test
    void shouldAddErrorByInvalidLength(){
        // 1. Arrange
        maxLength = 50;
        String email = "correocon_mas_de_20caracteres@correo.institucional.mx";
        // 2. Act

        Optional<Email> validEmail = Email.create(email, maxLength, "this email", notification);

        // 3. Assert
        System.out.println(validEmail.isEmpty() ? "Sin datos" : validEmail.get().toString());
        notification.getErrors().forEach(System.out::println);
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validEmail.isEmpty())
        );
    }

    @Test
    void shouldAddErrorByInvalidEmailFormat(){
        // 1. Arrange
        maxLength = 100;
        String email = "correo_no-validohotmail.com";
        // 2. Act
        Optional<Email> validEmail = Email.create(email, maxLength, "this email", notification);

        // 3. Assert
        System.out.println(validEmail.isEmpty() ? "Sin datos" : validEmail.get().toString());
        notification.getErrors().forEach(System.out::println);
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validEmail.isEmpty())
        );
    }
}