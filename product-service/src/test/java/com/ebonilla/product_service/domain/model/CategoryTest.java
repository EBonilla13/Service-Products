package com.ebonilla.product_service.domain.model;

import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

    @Test
    @DisplayName("Create category without ID")
    void shouldCreateCategoryWithoutId() {
        // 1. Arrange
        String name = "Department";

        // 2. Act
        Category category = Category.create(null, name, notification);

        // 3. Assert
        System.out.println(category == null ? "error" : category.toString());
        assertInstanceOf(Category.class, category);
    }

    @Test
    @DisplayName("Create category with ID")
    void shouldCreateCategoryWithId() {
        // 1. Arrange
        String name = "Department";
        Integer id = 100;

        // 2. Act
        Category category = Category.create(id, name, notification);

        // 3. Assert
        System.out.println(category == null ? "error" : category.toString());
        assertAll(
                () -> assertFalse(notification.hasErrors()),
                () -> assertNotNull(category.getId()),
                () -> assertNotNull(category.getCategoryName())
        );
    }

    @Test
    @DisplayName("Add errors to notifications")
    void shouldAddValidationErrors() {
        // 1. Arrange
        String name = "este es una categoria muy larga para sobre pasar el limite permitido de la categoria, hasta el momento la validacion es de cien letras.";
        Integer id = -1;

        // 2. Act
        Category category = Category.create(id, name, notification);

        // 3. Assert
        System.out.println(category == null ? "error" : category.toString());
        notification.getErrors().forEach(System.out::println);
        assertAll(
                () -> assertTrue(notification.hasErrors()),
                () -> assertNull(category)
        );
    }
}