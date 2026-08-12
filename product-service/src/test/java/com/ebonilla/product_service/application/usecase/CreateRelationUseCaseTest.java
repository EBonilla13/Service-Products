package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.productsupplier.request.PSRelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.request.Product;
import com.ebonilla.product_service.application.dto.productsupplier.request.RelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.request.Supplier;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.mapper.PSRelationMapper;
import com.ebonilla.product_service.application.ports.output.*;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import com.ebonilla.product_service.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRelationUseCaseTest {

    @Mock private ICategoryPort categoryPort;
    @Mock private IMeasurementPort measurementPort;
    @Mock private IProductPort productPort;
    @Mock private ISupplierPort supplierPort;
    @Mock private IProductSupplierPort relationPort;

    @InjectMocks private CreateRelationUseCase createRelationUseCase;

    private PSRelationRequestDto requestDto;
    private Product productRequest;
    private Supplier supplierRequest;
    private RelationRequestDto relationReq;

    @BeforeEach
    void setUp() {
        // Inicialización de DTOs anidados para los tests
        productRequest = new Product();
        productRequest.setName("llanta");
        productRequest.setModel("autofast");
        productRequest.setSpec("170/65 17");
        productRequest.setCategoryId(1);
        productRequest.setMeasurementId(2);

        supplierRequest = new Supplier();
        supplierRequest.setName("bodega aurrera");
        supplierRequest.setPhone("8123219987");
        supplierRequest.setEmail("b-aurrera1235@gmail.com");

        relationReq = new RelationRequestDto();
        relationReq.setPrice(new BigDecimal("3499.99"));

        requestDto = new PSRelationRequestDto();
        requestDto.setProduct(productRequest);
        requestDto.setSupplier(supplierRequest);
        requestDto.setRelation(relationReq);
    }

    @Test
    @DisplayName("create product, supplier y relation")
    void createSuccess() {
        // Mocks de dominio simulados
        com.ebonilla.product_service.domain.model.Product productMock = mock(com.ebonilla.product_service.domain.model.Product.class);
        com.ebonilla.product_service.domain.model.Supplier supplierMock = mock(com.ebonilla.product_service.domain.model.Supplier.class);
        ProductSupplier relationMock = mock(ProductSupplier.class);
        PSRelationResponseDto expectedResponse = new PSRelationResponseDto(12, new BigDecimal("3499.99"), "smart tv", "hisense", "43 pulgadas",
                "bodega aurrera", "8123219987", "b-aurrera1235@gmail.com");

        when(productMock.getId()).thenReturn(10);
        when(supplierMock.getId()).thenReturn(20);

        // Mockear los métodos booleanos
        when(categoryPort.existsCategoryById(productRequest.getCategoryId())).thenReturn(true);
        when(measurementPort.exists(productRequest.getMeasurementId())).thenReturn(true);

        // Mockear los puertos de persistencia
        when(productPort.create(any(com.ebonilla.product_service.domain.model.Product.class))).thenReturn(productMock);
        when(supplierPort.create(any(com.ebonilla.product_service.domain.model.Supplier.class))).thenReturn(supplierMock);
        when(relationPort.create(any(ProductSupplier.class))).thenReturn(relationMock);

        // Mockear los métodos estáticos del Mapper
        try (MockedStatic<PSRelationMapper> mapperMock = mockStatic(PSRelationMapper.class)) {
            mapperMock.when(() -> PSRelationMapper.toProductDomain(eq(productRequest), any(Notification.class))).thenReturn(productMock);
            mapperMock.when(() -> PSRelationMapper.toSupplierDomain(eq(supplierRequest), any(Notification.class))).thenReturn(supplierMock);
            mapperMock.when(() -> PSRelationMapper.toPSDomain(eq(relationReq), eq(10), eq(20), any(Notification.class))).thenReturn(relationMock);
            mapperMock.when(() -> PSRelationMapper.toDto(productMock, supplierMock, relationMock)).thenReturn(expectedResponse);

            // Act
            PSRelationResponseDto result = createRelationUseCase.create(requestDto);

            // Assert
            assertNotNull(result);
            assertEquals(expectedResponse, result);
            verify(productPort, times(1)).create(any(com.ebonilla.product_service.domain.model.Product.class));
            verify(supplierPort, times(1)).create(any(com.ebonilla.product_service.domain.model.Supplier.class));
            verify(relationPort, times(1)).create(any(ProductSupplier.class));
        }
    }

    @Test
    @DisplayName("Add error messages by invalid product and supplier")
    void shouldAddErrorByProductAndSupplier(){
        // 1. Arrange
        productRequest.setModel(null);
        productRequest.setMeasurementId(0);
        supplierRequest.setPhone("81234348");

        PSRelationRequestDto request = new PSRelationRequestDto(productRequest, supplierRequest, relationReq);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> createRelationUseCase.create(request));

        // 3. Assert
        assertNotNull(exception);
        verifyNoInteractions(categoryPort, measurementPort, productPort, supplierPort, relationPort);
    }

    @Test
    @DisplayName("Not create by category ID")
    void shouldThrowExceptionByCategory(){
        // 1. Arrange
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(false);

        PSRelationRequestDto request = new PSRelationRequestDto(productRequest, supplierRequest, relationReq);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> createRelationUseCase.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verifyNoInteractions(measurementPort, productPort,supplierPort, relationPort);
    }

    @Test
    @DisplayName("Not create by measurement ID")
    void shouldThrowExceptionByMeasurement(){
        // 1. Arrange
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(true);
        when(measurementPort.exists(anyInt())).thenReturn(false);

        PSRelationRequestDto request = new PSRelationRequestDto(productRequest, supplierRequest, relationReq);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> createRelationUseCase.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, times(1)).exists(anyInt());
        verifyNoInteractions(productPort,supplierPort, relationPort);
    }

    @Test
    @DisplayName("Not create by validation relation")
    void shouldThrowExceptionByValidationRelation(){
        // 1. Arrange
        com.ebonilla.product_service.domain.model.Product mockProduct = mock(com.ebonilla.product_service.domain.model.Product.class);
        com.ebonilla.product_service.domain.model.Supplier mockSupplier = mock(com.ebonilla.product_service.domain.model.Supplier.class);
        when(categoryPort.existsCategoryById(anyInt())).thenReturn(true);
        when(measurementPort.exists(anyInt())).thenReturn(true);
        when(productPort.create(any(com.ebonilla.product_service.domain.model.Product.class))).thenReturn(mockProduct);
        when(supplierPort.create(any(com.ebonilla.product_service.domain.model.Supplier.class))).thenReturn(mockSupplier);

        PSRelationRequestDto request = new PSRelationRequestDto(productRequest, supplierRequest, relationReq);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> createRelationUseCase.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(categoryPort, times(1)).existsCategoryById(anyInt());
        verify(measurementPort, times(1)).exists(anyInt());
        verify(productPort, times(1)).create(any(com.ebonilla.product_service.domain.model.Product.class));
        verify(supplierPort, times(1)).create(any(com.ebonilla.product_service.domain.model.Supplier.class));
        verifyNoInteractions(relationPort);
    }
}