package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        this.notification =  new Notification();
    }

    @Test
    @DisplayName("Create a measurement successfully")
    void shouldCreateAMeasurement() {
        // 1. Arrange
        String unit = "kilogramo", symbol = "kg";

        // 2. Act
        Measurement measurement = Measurement.create(null, unit, symbol, this.notification);

        // 3. Assert
        assertNotNull(measurement);
        assertFalse(notification.hasErrors());
    }

    @Test
    @DisplayName("Add error messages to notification")
    void shouldAddMessagesToNotification() {
        // 1. Arrange
        Integer id = 0;
        String unit = "kilogramo", symbol = "kilogramo   ";

        // 2. Act
        Measurement measurement = Measurement.create(id, unit, symbol, this.notification);

        // 3. Assert
        System.out.println(notification.getErrors());
        assertNull(measurement);
        assertTrue(notification.hasErrors());
    }
}