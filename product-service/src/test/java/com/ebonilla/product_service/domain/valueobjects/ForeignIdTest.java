package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ForeignIdTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        this.notification = new Notification();
    }

    @Test
    @DisplayName("Create valid foreign ID")
    void shouldCreateForeignId() {
        // 1. Arrange
        Integer id = 13;
        String reference = "Foreign ID";

        // 2. Act
        Optional<ForeignId> validID = ForeignId.create(id, reference, notification);

        // 3. Assert
        assertTrue(validID.isPresent());
        assertFalse(notification.hasErrors());
    }

    @Test
    @DisplayName("Add error message for null id")
    void shouldAddErrorMessageForNullId() {
        // 1. Arrange
        Integer id = null;
        String reference = "Foreign ID";

        // 2. Act
        Optional<ForeignId> validID = ForeignId.create(id, reference, notification);

        // 3. Assert
        assertTrue(validID.isEmpty());
        assertTrue(notification.hasErrors());
    }

    @Test
    @DisplayName("Add error message for invalid number")
    void shouldAddErrorMessageForInvalidNumber() {
        // 1. Arrange
        Integer id = 0;
        String reference = "Foreign ID";

        // 2. Act
        Optional<ForeignId> validID = ForeignId.create(id, reference, notification);

        // 3. Assert
        assertTrue(validID.isEmpty());
        assertTrue(notification.hasErrors());
    }
}