package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    private String reference;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        reference = "This price";
    }

    @Test
    void shouldCreateANewPrice() {
        // 1. Arrange
        BigDecimal price = new BigDecimal("0.0");

        // 2. Act
        Optional<Price> validPrice = Price.create(price, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(validPrice.isPresent())
        );
    }

    @Test
    void shouldAddErrorByPriceIsLessThanZero() {
        // 1. Arrange
        BigDecimal price = new BigDecimal("-19.89");

        // 2. Act
        Optional<Price> validPrice = Price.create(price, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validPrice.isEmpty())
        );
    }

    @Test
    void shouldAddErrorByNullPrice() {
        // 1. Arrange
        BigDecimal price = null;

        // 2. Act
        Optional<Price> validPrice = Price.create(price, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(validPrice.isEmpty())
        );
    }
}