package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        this.notification = new Notification();
    }

    @Test
    void shouldCreateSupplier() {
        // 1. Arrange
        String name = "ferreteria san juan", phone = "8113148201", email = "ferreteria-san-juan@gmail.com";

        // 2. Act
        Supplier validSupplier = Supplier.create(null, name, phone, email, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertNotNull(validSupplier)
        );
    }

    @Test
    void shouldAddErrorMessagesToNotification() {
        // 1. Arrange
        Integer id = 23;
        String name = "ferreteria san juan", phone = "8113148201123";

        // 2. Act
        Supplier validSupplier = Supplier.create(null, name, phone, null, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertNull(validSupplier)
        );
    }
}