package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TextTest {

    private String reference;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        reference = "This text";
    }

    @Test
    void shouldCreateANewText() {
        // 1. Arrange
        String text = "Ejemplo de texto para el test";
        Integer maxLength = 100;

        // 2. Act
        Optional<Text> validText = Text.create(text, maxLength, reference, false, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(validText.isPresent())
        );
    }

    @Test
    void shouldCreateANewTextNullable() {
        // 1. Arrange
        Integer maxLength = 100;

        // 2. Act
        Optional<Text> validText = Text.create(null, maxLength, reference, true, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(validText.isPresent())
        );
    }

    @Test
    void shouldAddErrorNotNull(){
        // 1. Arrange
        Integer maxLength = 100;

        // 2. Act
        Optional<Text> validText = Text.create(null, maxLength, reference, false, notification);

        // 3. Assert
        System.out.println(notification.getErrors());
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validText.isEmpty())
        );
    }

    @Test
    void shouldAddErrorByTextEqualsZero() {
        // 1. Arrange
        String text = "";
        Integer maxLength = 100;

        // 2. Act
        Optional<Text> validText = Text.create(text, maxLength, reference, false, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validText.isEmpty())
        );
    }

    @Test
    void shouldAddErrorByLongerLength() {
        // 1. Arrange
        String text = "este es un texto para la prueba que sea mayor";
        Integer maxLength = 40;

        // 2. Act
        Optional<Text> validText = Text.create(text, maxLength, reference, false, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validText.isEmpty())
        );
    }
}