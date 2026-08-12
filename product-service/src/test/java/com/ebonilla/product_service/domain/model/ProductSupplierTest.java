package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductSupplierTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        this.notification = new Notification();
    }

    @Test
    void shouldCreateProductSupplier() {
        // 1. Arrange
        BigDecimal price = new BigDecimal("149.99");
        Integer productId = 12, supplierId = 45;

        // 2. Act
        ProductSupplier validObject = ProductSupplier.create(null, price, productId, supplierId, notification);

        // 3. Assert
        assertAll(
                () -> assertNotNull(validObject),
                () -> assertFalse(notification.hasErrors())
        );
    }

    @Test
    void shouldAddErrorMessagesToNotification() {
        // 1. Arrange
        BigDecimal price = new BigDecimal("-119.00");
        Integer productId = 12, supplierId = null;

        // 2. Act
        ProductSupplier validObject = ProductSupplier.create(null, price, productId, supplierId, notification);

        // 3. Assert
        assertAll(
                () -> assertNull(validObject),
                () -> assertTrue(notification.hasErrors())
        );
    }
}