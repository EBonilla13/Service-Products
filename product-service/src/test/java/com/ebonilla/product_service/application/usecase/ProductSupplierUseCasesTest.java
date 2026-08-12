package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.productsupplier.request.ProductSupplierRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSupplierResponseDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.IPSRelationPort;
import com.ebonilla.product_service.application.ports.output.IProductPort;
import com.ebonilla.product_service.application.ports.output.IProductSupplierPort;
import com.ebonilla.product_service.application.ports.output.ISupplierPort;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductSupplierUseCasesTest {

    @Mock private IProductSupplierPort productSupplierPort;
    @Mock private IPSRelationPort psRelationPort;
    @Mock private IProductPort productPort;
    @Mock private ISupplierPort supplierPort;

    @InjectMocks private ProductSupplierUseCases productSupplierUseCases;

    private ProductSupplierRequestDto request;
    private ProductSupplier productSupplier;

    @BeforeEach
    void setUp() {
        request = new ProductSupplierRequestDto();
        request.setProductId(13);
        request.setSupplierId(34);
        request.setPrice(new BigDecimal("148.95"));

        productSupplier = new ProductSupplier();
        productSupplier.setId(18);
        productSupplier.setProductId(13);
        productSupplier.setSupplierId(34);
        productSupplier.setPrice(new BigDecimal("148.95"));
    }

    @Test
    @DisplayName("Create a relation")
    void shouldCreateRelation() {
        // 1. Arrange
        when(productPort.exists(anyInt())).thenReturn(true);
        when(supplierPort.exists(anyInt())).thenReturn(true);
        when(productSupplierPort.create(any())).thenReturn(productSupplier);

        // 2. Act
        ProductSupplierResponseDto response = productSupplierUseCases.create(request);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).exists(anyInt());
        verify(supplierPort, times(1)).exists(anyInt());
        verify(productSupplierPort, times(1)).create(any());
    }

    @Test
    @DisplayName("not create a relation by product not found")
    void shouldThrowExceptionByProductNotFoundWHenCreateRelation() {
        // 1. Arrange
        when(productPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).exists(anyInt());
        verify(supplierPort, never()).exists(anyInt());
        verify(productSupplierPort, never()).create(any());
    }

    @Test
    @DisplayName("not create a relation by supplier not found")
    void shouldThrowExceptionBySupplierNotFoundWHenCreateRelation() {
        // 1. Arrange
        when(productPort.exists(anyInt())).thenReturn(true);
        when(supplierPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).exists(anyInt());
        verify(supplierPort, times(1)).exists(anyInt());
        verify(productSupplierPort, never()).create(any());
    }

    @Test
    @DisplayName("not create a relation by validations")
    void shouldThrowBusinessLogicExceptionWHenCreateRelation() {
        // 1. Arrange
        request.setProductId(0);
        request.setPrice(null);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> productSupplierUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, never()).exists(anyInt());
        verify(supplierPort, never()).exists(anyInt());
        verify(productSupplierPort, never()).create(any());
    }

    @Test
    @DisplayName("Update a relation")
    void shouldUpdate() {
        // 1. Arrange
        request.setId(18);
        when(productPort.exists(anyInt())).thenReturn(true);
        when(supplierPort.exists(anyInt())).thenReturn(true);
        when(productSupplierPort.update(any())).thenReturn(productSupplier);

        // 2. Act
        ProductSupplierResponseDto response = productSupplierUseCases.update(request);

        // 3. Assert
        assertNotNull(response);
        verify(productPort, times(1)).exists(anyInt());
        verify(supplierPort, times(1)).exists(anyInt());
        verify(productSupplierPort, times(1)).update(any());
    }

    @Test
    @DisplayName("not update by null id")
    void shouldThrowIdNullExceptionWhenUpdateRelation() {
        // 2. Act
        IdNullException exception = assertThrowsExactly( IdNullException.class,
                () -> productSupplierUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, never()).exists(anyInt());
        verify(supplierPort, never()).exists(anyInt());
        verify(productSupplierPort, never()).update(any());
    }

    @Test
    @DisplayName("not update by product not found")
    void shouldThrowProductNotFoundWhenUpdateRelation() {
        // 1. Arrange
        request.setId(18);
        when(productPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).exists(anyInt());
        verify(supplierPort, never()).exists(anyInt());
        verify(productSupplierPort, never()).update(any());
    }

    @Test
    @DisplayName("not update by supplier not found")
    void shouldThrowSupplierNotFoundWhenUpdateRelation() {
        // 1. Arrange
        request.setId(18);
        when(productPort.exists(anyInt())).thenReturn(true);
        when(supplierPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, times(1)).exists(anyInt());
        verify(supplierPort, times(1)).exists(anyInt());
        verify(productSupplierPort, never()).update(any());
    }

    @Test
    @DisplayName("not update by validations")
    void shouldThrowBusinessLogicExceptionWhenUpdateRelation() {
        // 1. Arrange
        request.setId(0);
        request.setSupplierId(null);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> productSupplierUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(productPort, never()).exists(anyInt());
        verify(supplierPort, never()).exists(anyInt());
        verify(productSupplierPort, never()).update(any());
    }

    @Test
    @DisplayName("Relation found by id")
    void shouldFindById() {
        // 1. Arrange
        Integer id = 18;
        ProductSupplierResponseDto responseDto = new ProductSupplierResponseDto(id, request.getPrice());
        when(productSupplierPort.findById(anyInt())).thenReturn(Optional.of(responseDto));

        // 2. Act
        ProductSupplierResponseDto response = productSupplierUseCases.findById(id);

        // 3. Assert
        assertNotNull(response);
        verify(productSupplierPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Relation not found by id")
    void shouldThrowExceptionWhenFindById() {
        // 1. Arrange
        Integer id = 1200;

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.findById(id));

        // 3. Assert
        assertNotNull(exception);
        verify(productSupplierPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Relation found by foreign keys")
    void findByForeignKeys() {
        // 1. Arrange
        Integer productId = 13, supplierId = 34;
        PSRelationResponseDto responseDto = new PSRelationResponseDto(18, request.getPrice(), "lavadora", "mabe", "15 kg",
                "elizondo", "8123451234", "elizondo-nl@gmail.com");
        when(psRelationPort.findByForeignKeys(anyInt(), anyInt())).thenReturn(Optional.of(responseDto));

        // 2. Act
        PSRelationResponseDto response = productSupplierUseCases.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertNotNull(response);
        verify(psRelationPort, times(1)).findByForeignKeys(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Relation Not found by foreign keys")
    void shouldThrowExceptionWhenFindByForeignKeys() {
        // 1. Arrange
        Integer productId = 130, supplierId = 200;

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.findByForeignKeys(productId, supplierId));

        // 3. Assert
        assertNotNull(exception);
        verify(psRelationPort, times(1)).findByForeignKeys(anyInt(), anyInt());

    }

    @Test
    @DisplayName("Suppliers by product ID")
    void spSuppliersFindByProduct() {
        // 1. Arrange
        Integer productId = 13;
        SPSuppliersByFindProductDto responseDto = new SPSuppliersByFindProductDto(productId, "lavadora", "mabe", "15 kg", "electronica", "pza",
                List.of(new SPSupplierResponseDto("elizondo", "8123422819", "elizondo-nl@gmail.com", request.getPrice()),
                new SPSupplierResponseDto("elecktra", "8167239899", "mundo-elecktra@yahoo.com", new BigDecimal("178.48"))));

        when(psRelationPort.spSuppliersByProduct(anyInt())).thenReturn(Optional.of(responseDto));

        // 2. Act
        SPSuppliersByFindProductDto response = productSupplierUseCases.spSuppliersFindByProduct(productId);

        // 3. Assert
        assertNotNull(response);
        verify(psRelationPort, times(1)).spSuppliersByProduct(anyInt());
    }

    @Test
    @DisplayName("Suppliers not found by product ID")
    void shouldThrowExceptionWhenSpSuppliersFindByProduct() {
        // 1. Arrange
        Integer productId = 13;

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> productSupplierUseCases.spSuppliersFindByProduct(productId));

        // 3. Assert
        assertNotNull(exception);
        verify(psRelationPort, times(1)).spSuppliersByProduct(anyInt());
    }

    @Test
    @DisplayName("Return List of product, suppliers and pivot")
    void productFindBySupplier() {
        // 1. Arrange
        Integer supplierId = 34;
        String supplierName = "elizondo", phone = "8123422819", email = "elizondo-nl@gmail.com";
        ProductByFindSupplierDto responseDto1 = new ProductByFindSupplierDto(18, "lavadora", "mabe", "13 kg", "electronica", "pza", 17,
                request.getPrice(), supplierName, phone, email);
        ProductByFindSupplierDto responseDto2 = new ProductByFindSupplierDto(34, "celular", "iphone 13", "16 Gb RAM 250 GB ROM ", "electronica", "pza", 87,
                request.getPrice(), supplierName, phone, email);
        List<ProductByFindSupplierDto> list = List.of(responseDto1, responseDto2);
        when(psRelationPort.productBySupplier(anyInt())).thenReturn(list);

        // 2. Act
        List<ProductByFindSupplierDto> response = productSupplierUseCases.productFindBySupplier(supplierId);

        // 3. Assert
        assertNotNull(response);
        verify(psRelationPort, times(1)).productBySupplier(anyInt());

    }
}