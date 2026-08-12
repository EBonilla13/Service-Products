package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        this.notification = new Notification();
    }

    @Test
    @DisplayName("Create a product successfully")
    void shouldCreateProduct() {
        // 1. Arrange
        Integer categoryId = 7, measurementId = 10;
        String name = "televisor", model = "samsung";

        // 2. Act
        Product product = Product.create(null, name, model, null, categoryId, measurementId, notification);

        // 3. Assert
        assertNotNull(product);
        assertFalse(notification.hasErrors());
    }

    @Test
    @DisplayName("Add error messages to notification")
    void shouldAddErrorMessagesToNotification() {
        // 1. Arrange
        Integer categoryId = null,  measurementId = 10;;
        String name = "televisor", model = "";

        // 2. Act
        Product product = Product.create(null, name, model, null, categoryId, measurementId, notification);

        // 3. Assert
        assertNull(product);
        assertTrue(notification.hasErrors());
    }
}