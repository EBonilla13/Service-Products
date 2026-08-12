package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.ICategoryPort;
import com.ebonilla.product_service.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCasesTest {

    @Mock
    private ICategoryPort iCategoryPort;

    @InjectMocks
    private CategoryUseCases categoryUseCases;

    private CategoryRequestDto requestDto;
    private Category categorySaved;

    @BeforeEach
    void setUp() {
        requestDto = new CategoryRequestDto();
        requestDto.setName("limpieza");

        categorySaved = new Category();
        categorySaved.setId(1);
        categorySaved.setCategoryName("limpieza");
    }

    @Test
    @DisplayName("Create a new category")
    void shouldCreateCategory(){
        // 1. Arrange
        when(iCategoryPort.save(any(Category.class))).thenReturn(categorySaved);

        // 2. Act
        CategoryResponseDto response = this.categoryUseCases.create(this.requestDto);

        // 3. Assert
        assertNotNull(response);
        verify(iCategoryPort, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Return error message")
    void shouldReturnErrorMessage(){
        // 1. Arrange
        this.requestDto.setName("");

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> this.categoryUseCases.create(this.requestDto));

        // 3. Assert
        assertNotNull(exception);
        verify(iCategoryPort, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Update a category")
    void shouldUpdateCategory(){
        // 1. Arrange
        requestDto.setId(1);
        when(iCategoryPort.save(any(Category.class))).thenReturn(categorySaved);

        // 2. Act
        CategoryResponseDto response = this.categoryUseCases.update(this.requestDto);

        // 3. Assert
        assertNotNull(response);
        assertAll(
                () -> assertEquals(requestDto.getId(), response.getId()),
                () -> assertEquals(requestDto.getName(), response.getName())
        );
        verify(iCategoryPort, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Return error by null ID")
    void shouldReturnErrorMessageByNullId(){

        // 2. Act and Assert
        IdNullException exception = assertThrowsExactly( IdNullException.class,
                () -> this.categoryUseCases.update(this.requestDto));

        assertNotNull(exception);
        verify(iCategoryPort, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Return validation error message")
    void shouldReturnValidationErrorMessage(){
        // 1. Arrange
        requestDto.setId(0);
        requestDto.setName("this is a word that must have more than one hundred characters to validate error messages in case of an update");

        // 2. Act and Assert
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> this.categoryUseCases.update(this.requestDto));

        assertNotNull(exception);
        verify(iCategoryPort, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Search by id successfully")
    void shouldReturnCategoryWhenFindById(){
        // 1. Arrange
        Integer id = 1;
        CategoryResponseDto responseDto = new CategoryResponseDto(categorySaved.getId(), categorySaved.getCategoryName()
                , Instant.now().minusSeconds(432000), Instant.now().minusSeconds(345600));
        when(iCategoryPort.findById(anyInt())).thenReturn(Optional.of(responseDto));

        // 2. Act
        CategoryResponseDto response = categoryUseCases.findById(id);

        // 3. Assert
        assertNotNull(response);
        verify(iCategoryPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Category not found by ID")
    void shouldReturnErrorWhenFindById(){
        // 1. Arrange
        Integer id = 100;

        // 2. Act and Assert
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> this.categoryUseCases.findById(id));

        assertNotNull(exception);
        verify(iCategoryPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("List of categories")
    void shouldReturnListOfCategories(){
        // 1. Arrange
        CategoryResponseDto responseDto1 = new CategoryResponseDto(1, "tecnologia", null, null);
        CategoryResponseDto responseDto2 = new CategoryResponseDto(2, "limpieza", null, null);
        List<CategoryResponseDto> responseList = List.of(responseDto1, responseDto2);
        when(iCategoryPort.categories()).thenReturn(responseList);

        // 2. Act
        List<CategoryResponseDto> responseDtoList = categoryUseCases.categories();

        // 3. Assert
        assertNotNull(responseDtoList);
        verify(iCategoryPort, times(1)).categories();
    }

    @Test
    @DisplayName("List of categories empty")
    void shouldReturnListEmpty(){
        // 1. Arrange
        List<CategoryResponseDto> responseList = new ArrayList<>();
        when(iCategoryPort.categories()).thenReturn(responseList);

        // 2. Act
        List<CategoryResponseDto> responseDtoList = categoryUseCases.categories();

        // 3. Assert
        assertEquals(0, responseList.size());
        verify(iCategoryPort, times(1)).categories();
    }
}