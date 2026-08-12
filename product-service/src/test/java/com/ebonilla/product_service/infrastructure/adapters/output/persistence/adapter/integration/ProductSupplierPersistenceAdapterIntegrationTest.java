package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.integration;

import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.ProductSupplierPersistenceAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig( transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Sql(scripts = "/database/data_save.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig( transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Import(ProductSupplierPersistenceAdapter.class)
class ProductSupplierPersistenceAdapterIntegrationTest extends BaseRepositoryTest {

    @Autowired private ProductSupplierPersistenceAdapter adapter;

    @Test
    @DisplayName("Create a product supplier relation")
    void shouldCreateAProductSupplier(){
        // 1. Arrange
        BigDecimal price = new BigDecimal("5999.99");
        Integer productId = 1, supplierId = 2;
        ProductSupplier domain = ProductSupplier.create(null, price, productId, supplierId, new Notification());

        // 2. Act
        assertNotNull(domain);
        ProductSupplier saved = adapter.create(domain);

        // 3. Assert
        assertNotNull(saved);
        assertAll(
                () -> assertNotNull(saved.getId()),
                () -> assertEquals(price, saved.getPrice()),
                () -> assertEquals(productId, saved.getProductId()),
                () -> assertEquals(supplierId, saved.getSupplierId())
        );
    }

    @Test
    @DisplayName("Update a product supplier relation")
    void shouldUpdateAProductSupplier(){
        // 1. Arrange
        BigDecimal price = new BigDecimal("5999.99");
        Integer id = 1, productId = 1, supplierId = 2;
        Boolean exists = adapter.exists(id);
        ProductSupplier domain = ProductSupplier.create(exists ? id : null, price, productId, supplierId, new Notification());

        // 2. Act
        assertNotNull(domain);
        ProductSupplier saved = adapter.update(domain);

        // 3. Assert
        assertNotNull(saved);
        assertAll(
                () -> assertEquals(id, saved.getId()),
                () -> assertEquals(price, saved.getPrice()),
                () -> assertEquals(productId, saved.getProductId()),
                () -> assertEquals(supplierId, saved.getSupplierId())
        );
    }

    @Test
    @DisplayName("Update fail because relation not found by id")
    void shouldThrowResourceNotFoundExceptionWhenUpdate(){
        // 1. Arrange
        BigDecimal price = new BigDecimal("5999.99");
        Integer id = 10, productId = 1, supplierId = 2;
        ProductSupplier domain = ProductSupplier.create(id, price, productId, supplierId, new Notification());

        // 2. Act
        ResourceNotFoundException ex = assertThrowsExactly( ResourceNotFoundException.class,
                () -> adapter.update(domain));

        // 3. Assert
        assertNotNull(ex);
        assertThat(ex.getMessage()).contains(String.valueOf(id));
    }

    @Test
    @DisplayName("Get product supplier by id")
    void shouldFindProductSupplierBy(){
        // 1. Arrange
        Integer id = 4;

        // 2. Act
        Optional<ProductSupplierResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isPresent());
        assertEquals(id, response.get().getId());
    }

    @Test
    @DisplayName("Product supplier not found by id")
    void shouldReturnOptionalEmptyWhenFindId(){
        // 1. Arrange
        Integer id = 20;

        // 2. Act
        Optional<ProductSupplierResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Return True when find by id")
    void shouldReturnTrueWhenFindById(){
        // 1. Arrange
        Integer id = 6;

        // 2. Act
        Boolean response = adapter.exists(id);

        // 3. Assert
        assertTrue(response);
    }

    @Test
    @DisplayName("Return False when find by id")
    void shouldReturnFalseWhenFindById(){
        // 1. Arrange
        Integer id = 100;

        // 2. Act
        Boolean response = adapter.exists(id);

        // 3. Assert
        assertFalse(response);
    }
}