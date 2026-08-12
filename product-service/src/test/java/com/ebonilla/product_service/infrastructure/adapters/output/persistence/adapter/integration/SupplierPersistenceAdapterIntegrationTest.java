package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.integration;

import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.domain.model.Supplier;
import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.SupplierPersistenceAdapter;
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
@Sql(scripts = "/database/data_supplier.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig( transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Import(SupplierPersistenceAdapter.class)
class SupplierPersistenceAdapterIntegrationTest extends BaseRepositoryTest {

    @Autowired private SupplierPersistenceAdapter adapter;

    private Supplier supplierDomain;

    @Test
    @DisplayName("Create a supplier")
    void shouldCreateASupplier(){
        // 1. Arrange
        supplierDomain = Supplier.create(null, "supplier name", "8112131418", "supplier_name12@gmail.com", new Notification());

        // 2. Act
        Supplier supplierSaved = adapter.create(supplierDomain);

        // 3. Assert
        assertNotNull(supplierSaved);
        assertAll(
                () -> assertThat(supplierSaved.getId()).isNotNull(),
                () -> assertThat(supplierSaved.getSupplierName()).isEqualTo(supplierDomain.getSupplierName()),
                () -> assertThat(supplierSaved.getNumberPhone()).isEqualTo(supplierDomain.getNumberPhone()),
                () -> assertThat(supplierSaved.getEmail()).isEqualTo(supplierDomain.getEmail())
        );
    }

    @Test
    @DisplayName("Update a supplier")
    void shouldUpdateASupplier(){
        // 1. Arrange
        Integer supplierId = 2;
        Boolean exists = adapter.exists(supplierId);
        supplierDomain = Supplier.create(exists ? supplierId : null, "supplier name", "8112131418", "supplier_name12@gmail.com", new Notification());

        // 2. Act
        Supplier supplierSaved = adapter.update(supplierDomain);

        // 3. Assert
        assertNotNull(supplierSaved);
        assertAll(
                () -> assertEquals(supplierId, supplierSaved.getId()),
                () -> assertThat(supplierSaved.getSupplierName()).isEqualTo(supplierDomain.getSupplierName()),
                () -> assertThat(supplierSaved.getNumberPhone()).isEqualTo(supplierDomain.getNumberPhone()),
                () -> assertThat(supplierSaved.getEmail()).isEqualTo(supplierDomain.getEmail())
        );
    }

    @Test
    @DisplayName("Update fail by not found by id")
    void shouldThrowResourceNotFoundExceptionWhenUpdate(){
        // 1. Arrange
        Integer supplierId = 29;
        supplierDomain = Supplier.create(supplierId, "supplier name", "8112131418", "supplier_name12@gmail.com", new Notification());

        // 2. Act
        ResourceNotFoundException ex = assertThrowsExactly( ResourceNotFoundException.class,
                () -> adapter.update(supplierDomain));

        // 3. Assert
        assertNotNull(ex);
        assertThat(ex.getMessage()).contains(String.valueOf(supplierId));
    }

    @Test
    @DisplayName("Supplier found by id")
    void shouldGetSupplierById(){
        // 1. Arrange
        Integer supplierId = 3;

        // 2. Act
        Optional<SupplierResponseDto> response = adapter.findById(supplierId);

        // 3. Assert
        assertTrue(response.isPresent());
        assertEquals(supplierId, response.get().getId());
    }

    @Test
    @DisplayName("Supplier not found by id")
    void shouldReturnEmptyOptionalWhenFindById(){
        // 1. Arrange
        Integer supplierId = 10;

        // 2. Act
        Optional<SupplierResponseDto> response = adapter.findById(supplierId);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Supplier found by name")
    void shouldGetSupplierByName(){
        // 1. Arrange
        String name = "elizondo";

        // 2. Act
        Optional<SupplierResponseDto> response = adapter.findByName(name);

        // 3. Assert
        assertTrue(response.isPresent());
        assertEquals(name, response.get().getName());
    }

    @Test
    @DisplayName("Supplier not found by name")
    void shouldReturnEmptyOptionalWhenFindByName(){
        // 1. Arrange
        String name = "soriana";

        // 2. Act
        Optional<SupplierResponseDto> response = adapter.findByName(name);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Supplier exists by id")
    void shouldReturnTrueWhenFindById(){
        // 1. Arrange
        Integer supplierId = 1;

        // 2. Act
        Boolean response = adapter.exists(supplierId);

        // 3. Assert
        assertTrue(response);
    }

    @Test
    @DisplayName("Supplier not exists by id")
    void shouldReturnFalseWhenFindById(){
        // 1. Arrange
        Integer supplierId = 100;

        // 2. Act
        Boolean response = adapter.exists(supplierId);

        // 3. Assert
        assertFalse(response);
    }

    @Test
    @DisplayName("List of suppliers")
    void shouldReturnListOfSuppliers(){

        // 2. Act
        List<SupplierResponseDto> response = adapter.suppliers();

        // 3. Assert
        assertFalse(response.isEmpty());
        assertThat(response.size()).isEqualTo(4);
    }
}