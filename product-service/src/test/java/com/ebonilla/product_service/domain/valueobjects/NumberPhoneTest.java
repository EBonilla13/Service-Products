package com.ebonilla.product_service.domain.valueobjects;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NumberPhoneTest {

    private String reference;
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        reference = "this Number Phone";
    }

    @Test
    void shouldCreateANewNumberPhoneWithTenDigits(){
        // 1. Arrange
        String number = "8121581338";

        // 2. Act
        Optional<NumberPhone> numberPhone = NumberPhone.create(number, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(numberPhone.isPresent())
        );
    }

    @Test
    void shouldCreateANewNumberPhoneWithTwelveDigits(){
        // 1. Arrange
        String number = "518121581338";

        // 2. Act
        Optional<NumberPhone> numberPhone = NumberPhone.create(number, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertTrue(numberPhone.isPresent())
        );
    }

    @Test
    void shouldAddErrorByInvalidLength(){
        // 1. Arrange
        String number = "1581338";

        // 2. Act
        Optional<NumberPhone> numberPhone = NumberPhone.create(number, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(numberPhone.isEmpty())
        );
    }

    @Test
    void shouldAddErrorByInvalidCharacters(){
        // 1. Arrange
        String number = "8116178878a$";

        // 2. Act
        Optional<NumberPhone> numberPhone = NumberPhone.create(number, reference, notification);

        // 3. Assert
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertTrue(numberPhone.isEmpty())
        );
    }
}