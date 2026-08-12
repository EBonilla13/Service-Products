package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.integration;

import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.domain.model.Category;
import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.CategoryPersistenceAdapter;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ICategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Sql(scripts = "/database/data_category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Import(value = CategoryPersistenceAdapter.class)
class CategoryPersistenceAdapterIntegrationTest extends BaseRepositoryTest {

    @Autowired private CategoryPersistenceAdapter categoryPersistenceAdapter;
    @Autowired private ICategory iCategory;

    private Category categoryDomain;

    @Test
    @DisplayName("Create a Category")
    void shouldCreateACategory(){
        // 1. Arrange
        categoryDomain = Category.create(null, "aceites", new Notification());

        // 2. Act
        Category responseCategory = categoryPersistenceAdapter.save(categoryDomain);

        // 3. Assert
        assertThat(responseCategory).as("create category fail").isNotNull();
        assertAll(
                () -> assertThat(responseCategory.getId()).isNotNull(),
                () -> assertThat(responseCategory.getCategoryName()).isEqualTo(categoryDomain.getCategoryName())
        );
    }

    @Test
    @DisplayName("Update a Category")
    void shouldUpdateACategory(){
        // 1. Arrange
        List<CategoryResponseDto> categories = categoryPersistenceAdapter.categories();
        categoryDomain = Category.create(categories.get(1).getId(), "piezas maquinaria", new Notification());

        // 2. Act
        Category responseCategory = categoryPersistenceAdapter.update(categoryDomain);

        // 3. Assert
        assertNotNull(responseCategory);
        assertAll(
                () -> assertEquals(categoryDomain.getId(), responseCategory.getId()),
                () -> assertEquals(categoryDomain.getCategoryName(), responseCategory.getCategoryName())
        );
    }

    @Test
    @DisplayName("found category by Id")
    void shouldReturnCategoryWhenFindById(){
        // 1. Arrange
        Integer categoryId = 3;

        // 2. Act
        Optional<CategoryResponseDto> categoryFound = categoryPersistenceAdapter.findById(categoryId);

        // 3. Assert
        assertTrue(categoryFound.isPresent());
        assertThat(categoryFound.get().getId()).isEqualTo(categoryId);
        assertThat(categoryFound.get().getName()).isNotBlank();
        assertThat(categoryFound.get().getCreatedAt()).isInThePast();
    }

    @Test
    @DisplayName("not found category by Id")
    void shouldReturnEmptyWhenFindById(){
        // 1. Arrange
        Integer categoryId = 50;

        // 2. Act
        Optional<CategoryResponseDto> categoryFound = categoryPersistenceAdapter.findById(categoryId);

        // 3. Assert
        assertTrue(categoryFound.isEmpty());
    }

    @Test
    @DisplayName("Exists category by Id")
    void shouldReturnTrueWhenFindById(){
        // 2. Act
        Boolean categoryFound = categoryPersistenceAdapter.existsCategoryById(4);

        // 3. Assert
        assertTrue(categoryFound);
    }

    @Test
    @DisplayName("Not Exists category by Id")
    void shouldReturnFalseWhenFindById(){
        // 1. Arrange
        Integer categoryId = 10;

        // 2. Act
        Boolean categoryFound = categoryPersistenceAdapter.existsCategoryById(categoryId);

        // 3. Assert
        assertFalse(categoryFound);
    }

    @Test
    @DisplayName("List of categories")
    void shouldReturnListOfCategories(){
        // 2. Act
        List<CategoryResponseDto> categories = categoryPersistenceAdapter.categories();

        // 3. Assert
        assertThat(categories.size()).as("List empty").isGreaterThan(0);
    }
}