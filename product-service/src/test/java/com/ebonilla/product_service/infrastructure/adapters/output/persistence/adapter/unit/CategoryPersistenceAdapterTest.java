package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.unit;

import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.CategoryPersistenceAdapter;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Category;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ICategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryPersistenceAdapterTest {

    @Mock private ICategory repository;

    @InjectMocks
    private CategoryPersistenceAdapter adapter;

    private com.ebonilla.product_service.domain.model.Category categoryDomain;

    @BeforeEach
    void setUp() {
        categoryDomain = new com.ebonilla.product_service.domain.model.Category();
        categoryDomain.setCategoryName("tecnologia");
    }

    @Test
    @DisplayName("Create category")
    void shouldReturnACategory(){
        // 1. Arrange
        Category categorySaved = new Category(10, this.categoryDomain.getCategoryName());
        when(repository.save(any())).thenReturn(categorySaved);

        // 2. Act
        com.ebonilla.product_service.domain.model.Category response = adapter.save(categoryDomain);

        // 3. Assert
        assertNotNull(response);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Update a category")
    void shouldUpdateACategory(){
        // 1. Arrange
        this.categoryDomain.setId(15);
        Category category = new Category(15, "tools office");
        Category categorySaved = new Category(this.categoryDomain.getId(), this.categoryDomain.getCategoryName());

        when(repository.findById(anyInt())).thenReturn(Optional.of(category));
        when(repository.save(any())).thenReturn(categorySaved);

        // 2. Act
        com.ebonilla.product_service.domain.model.Category response = adapter.update(categoryDomain);

        // 3. Assert
        assertNotNull(response);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Error by category not found when update")
    void shouldReturnErrorByCategoryNotFound(){
        // 1. Arrange
        this.categoryDomain.setId(100);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> adapter.update(categoryDomain));

        // 3. Assert
        assertNotNull(exception);
        verify(repository, times(1)).findById(anyInt());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Find Category By ID")
    void shouldFindById(){
        // 1. Arrange
        Integer id = 15;
        Category categoryEntity = new Category(id, this.categoryDomain.getCategoryName());
        when(repository.findById(any())).thenReturn(Optional.of(categoryEntity));

        // 2. Act
        Optional<CategoryResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isPresent());
        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Return optional empty")
    void shouldReturnOptionalEmptyWhenFindById(){
        // 1. Arrange
        Integer id = 150;
        when(repository.findById(any())).thenReturn(Optional.empty());

        // 2. Act
        Optional<CategoryResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isEmpty());
        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Category exists")
    void shouldReturnTrue(){
        // 1. Arrange
        Integer id = 15;
        when(repository.existsById(anyInt())).thenReturn(true);

        // 2. Act
        java.lang.Boolean response = adapter.existsCategoryById(id);

        // 3. Assert
        assertTrue(response);
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    @DisplayName("Category not exists")
    void shouldReturnFalse(){
        // 1. Arrange
        Integer id = 15;
        when(repository.existsById(anyInt())).thenReturn(false);

        // 2. Act
        java.lang.Boolean response = adapter.existsCategoryById(id);

        // 3. Assert
        assertFalse(response);
        verify(repository, times(1)).existsById(anyInt());
    }

    @Test
    @DisplayName("List with categories")
    void shouldReturnListOfCategories(){
        // 1. Arrange
        Category categoryEntity1 = new Category(12, "limpieza");
        Category categoryEntity2 = new Category(13, "piezas");
        Category categoryEntity3 = new Category(14, "motores");
        List<Category> categories = Arrays.asList(categoryEntity1, categoryEntity2, categoryEntity3);

        when(repository.findAll()).thenReturn(categories);

        // 2. Act
        List<CategoryResponseDto> responses = adapter.categories();

        // 3. Assert
        assertEquals(3, responses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("List without categories")
    void shouldReturnListEmpty(){
        // 1. Arrange
        List<Category> categories = new ArrayList<>();

        when(repository.findAll()).thenReturn(categories);

        // 2. Act
        List<CategoryResponseDto> responses = adapter.categories();

        // 3. Assert
        assertEquals(0, responses.size());
        verify(repository, times(1)).findAll();
    }
}