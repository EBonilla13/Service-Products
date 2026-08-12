package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IdentifierTest {

    private String reference;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        reference = "This Id";
    }

    @Test
    void shouldCreateANewIdentifierWithIdNull() {
        // 1. Arrange
        Integer id = null;

        // 2. Act
        Optional<Identifier> identifier = Identifier.create(id, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(identifier.isPresent())
        );
    }

    @Test
    void shouldCreateANewIdentifier(){
        // 1. Arrange
        Integer id = 100;

        // 2. Act
        Optional<Identifier> identifier = Identifier.create(id, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(identifier.isPresent())
        );
    }

    @Test
    void shouldAddErrorByInvalidNumber(){
        // 1. Arrange
        Integer id = 0;

        // 2. Act
        Optional<Identifier> identifier = Identifier.create(id, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(identifier.isEmpty())
        );
    }
}