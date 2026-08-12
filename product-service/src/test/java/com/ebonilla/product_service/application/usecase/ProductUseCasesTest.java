package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.ICategoryPort;
import com.ebonilla.product_service.application.ports.output.IMeasurementPort;
import com.ebonilla.product_service.application.ports.output.IProductPort;
import com.ebonilla.product_service.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCasesTest {

    @Mock private IProductPort productPort;
    @Mock private ICategoryPort categoryPort;
    @Mock private IMeasurementPort measurementPort;

    @InjectMocks private ProductUseCases productUseCases;

    private ProductRequestDto request;
    private Product productSaved;

    @BeforeEach
    void setUp() {
        request = new ProductRequestDto();
        request.setName("faro derecho");
        request.setModel("ultatonic");
        request.setSpecification("12 pulgadas");
        request.setCategoryId(56);
        request.setMeasurementId(34);

        productSaved = new Product();
        productSaved.setId(14);
        productSaved.setProductName("faro derecho");
        productSaved.setProductModel("ultatonic");
        productSaved.setProductSpecification("12 pulgadas");
        productSaved.setCategoryId(56);
        productSaved.setMeasurementId(34);
    }

    @Test
    @DisplayName("Create a product")
    void shouldCreateProduct(){
        // 1. Arrange
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(true);
        when(measurementPort.exists(anyInt())).thenReturn(true);
        when(productPort.create(any(Product.class))).thenReturn(productSaved);

        // 2. Act
        ProductResponseDto response = productUseCases.create(request);

        // 3. Assert
        assertNotNull(response);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, times(1)).exists(anyInt());
        verify(productPort, times(1)).create(any());
    }

    @Test
    @DisplayName("create fail by category not found")
    void shouldThrowCategoryNotFoundWhenCreate(){
        // 1. Arrange
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, never()).exists(anyInt());
        verify(productPort, never()).create(any());
    }

    @Test
    @DisplayName("create fail by measurement not found")
    void shouldThrowMeasurementNotFoundWhenCreate(){
        // 1. Arrange
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(true);
        when(measurementPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, times(1)).exists(anyInt());
        verify(productPort, never()).create(any());
    }

    @Test
    @DisplayName("create fail by validations")
    void shouldThrowBusinessLogic(){
        // 1. Arrange
        request.setModel("");
        request.setCategoryId(0);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> productUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, never()).existsCategoryById(anyInt());
        verify(measurementPort, never()).exists(anyInt());
        verify(productPort, never()).create(any());
    }

    @Test
    @DisplayName("Update a product")
    void shouldUpdateProduct(){
        // 1. Arrange
        request.setId(2);
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(true);
        when(measurementPort.exists(anyInt())).thenReturn(true);
        when(productPort.update(any(Product.class))).thenReturn(productSaved);

        // 2. Act
        ProductResponseDto response = productUseCases.update(request);

        // 3. Assert
        assertNotNull(response);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, times(1)).exists(anyInt());
        verify(productPort, times(1)).update(any());
    }

    @Test
    @DisplayName("update fail by id null")
    void shouldThrowIdNullExceptionWhenUpdate(){
        // 2. Act
        IdNullException exception = assertThrowsExactly( IdNullException.class,
                () -> productUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, never()).existsCategoryById(anyInt());
        verify(measurementPort, never()).exists(anyInt());
        verify(productPort, never()).update(any());
    }

    @Test
    @DisplayName("update fail by category not found")
    void shouldThrowCategoryNotFoundWhenUpdate(){
        // 1. Arrange
        request.setId(2);
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, never()).exists(anyInt());
        verify(productPort, never()).update(any());
    }

    @Test
    @DisplayName("update fail by measurement not found")
    void shouldThrowMeasurementNotFoundWhenUpdate(){
        // 1. Arrange
        request.setId(2);
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(true);
        when(measurementPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, times(1)).exists(anyInt());
        verify(productPort, never()).update(any());
    }

    @Test
    @DisplayName("update fail by validations")
    void shouldThrowBusinessLogicWhenUpdate(){
        // 1. Arrange
        request.setId(-1234);
        request.setModel("");
        request.setMeasurementId(null);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> productUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, never()).existsCategoryById(anyInt());
        verify(measurementPort, never()).exists(anyInt());
        verify(productPort, never()).update(any());
    }

    @Test
    @DisplayName("product found by id")
    void shouldFindProductById(){
        // 1. Arrange
        Integer id = 4;
        ProductResponseDto responseDto = new ProductResponseDto(id, request.getName(), request.getModel(), request.getSpecification(),
                request.getCategoryId(), request.getMeasurementId(), null, null);
        when(productPort.findById(anyInt())).thenReturn(Optional.of(responseDto));

        // 2. Act
        ProductResponseDto response = productUseCases.findById(id);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("product not found by id")
    void shouldThrowExceptionWhenFindProductById(){
        // 1. Arrange
        Integer id = 100;

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.findById(id));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("product found by name")
    void shouldFindProductByName(){
        // 1. Arrange
        String name = "faro derecho";
        ProductResponseDto responseDto = new ProductResponseDto(13, request.getName(), request.getModel(), request.getSpecification(),
                request.getCategoryId(), request.getMeasurementId(), null, null);
        when(productPort.findByName(anyString())).thenReturn(Optional.of(responseDto));

        // 2. Act
        ProductResponseDto response = productUseCases.findByName(name);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("product not found by name")
    void shouldThrowExceptionWhenFindProductByName(){
        // 1. Arrange
        String name = "faro izquierdo";

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.findByName(name));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("product found by model")
    void shouldFindProductByModel(){
        // 1. Arrange
        String model = "ultatonic";
        ProductResponseDto responseDto = new ProductResponseDto(13, request.getName(), request.getModel(), request.getSpecification(),
                request.getCategoryId(), request.getMeasurementId(), null, null);
        when(productPort.findByModel(anyString())).thenReturn(Optional.of(responseDto));

        // 2. Act
        ProductResponseDto response = productUseCases.findByModel(model);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).findByModel(anyString());
    }

    @Test
    @DisplayName("product not found by model")
    void shouldThrowExceptionWhenFindProductByModel(){
        // 1. Arrange
        String model = "faro izquierdo";

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.findByModel(model));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).findByModel(anyString());
    }

    @Test
    @DisplayName("product found by specification")
    void shouldFindProductBySpecification(){
        // 1. Arrange
        String spec = "12 pulgadas";
        ProductResponseDto responseDto = new ProductResponseDto(13, request.getName(), request.getModel(), request.getSpecification(),
                request.getCategoryId(), request.getMeasurementId(), null, null);
        when(productPort.findBySpecification(anyString())).thenReturn(Optional.of(responseDto));

        // 2. Act
        ProductResponseDto response = productUseCases.findBySpecification(spec);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).findBySpecification(anyString());
    }

    @Test
    @DisplayName("product not found by specification")
    void shouldThrowExceptionWhenFindProductBySpecification(){
        // 1. Arrange
        String spec = "faro izquierdo";

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productUseCases.findBySpecification(spec));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).findBySpecification(anyString());
    }

    @Test
    @DisplayName("List of products")
    void shouldReturnListOfProducts(){
        // 1. Arrange
        ProductResponseDto responseDto1 = new ProductResponseDto(24, "lavadora", "samsung", "3 toneladas", 5, 2, null, null);
        ProductResponseDto responseDto2 = new ProductResponseDto(25, "abanico", "myair", "pedestal de 55 cm", 6, 3, null, null);
        List<ProductResponseDto> list = List.of(responseDto1, responseDto2);
        when(productPort.products()).thenReturn(list);

        // 2. Act
        List<ProductResponseDto> response = productUseCases.products();

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).products();
    }


    @Test
    @DisplayName("List of products by category")
    void shouldReturnListOfProductsByCategory(){
        // 1. Arrange
        Integer categoryId = 10;
        ProductResponseDto responseDto1 = new ProductResponseDto(24, "lavadora", "samsung", "3 toneladas", 10, 2, null, null);
        ProductResponseDto responseDto2 = new ProductResponseDto(19, "secadora", "mabe", "4 tiempos", 10, 3, null, null);
        List<ProductResponseDto> list = List.of(responseDto1, responseDto2);
        when(productPort.productsByCategoryId(anyInt())).thenReturn(list);

        // 2. Act
        List<ProductResponseDto> response = productUseCases.productsByCategoryId(categoryId);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).productsByCategoryId(anyInt());
    }
}