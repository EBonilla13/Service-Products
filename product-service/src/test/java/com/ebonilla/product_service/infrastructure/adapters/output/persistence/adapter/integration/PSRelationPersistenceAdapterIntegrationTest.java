package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.integration;

import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.PSRelationPersistenceAdapter;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.*;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProduct;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProductSupplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ISupplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Sql(scripts = "/database/data_save.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Import({PSRelationPersistenceAdapter.class, JacksonAutoConfiguration.class})
class PSRelationPersistenceAdapterIntegrationTest extends BaseRepositoryTest {

    @Autowired private PSRelationPersistenceAdapter adapter;
    @Autowired private IProduct iProduct;
    @Autowired private ISupplier iSupplier;
    @Autowired private IProductSupplier iProductSupplier;

    @Test
    @DisplayName("Get relation by product id and supplier id")
    void shouldReturnRelationWhenFindByProductIdAndSupplierId(){
//         1. Arrange
        Product product = iProduct.save(new Product(null, "estufa", "pioner", "gas 12 parrillas",
                new Category(3, "tecnologia"), new Measurement(3, "pieza", "pza")));
        Supplier supplier = iSupplier.save(new Supplier(null, "rac", null, "rac@gmail.com"));
        ProductSupplier productSupplier = iProductSupplier.save(new ProductSupplier(null, new BigDecimal("8999.59"), product, supplier));

        Integer productId = productSupplier.getProduct().getId(), supplierId = productSupplier.getSupplier().getId();

        // 2. Act
        Optional<PSRelationResponseDto> response = adapter.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isPresent());
        assertAll(
                () -> assertThat(response.get().getId()).isNotNull(),
                () -> assertThat(response.get().getPrice()).isNotNull(),
                () -> assertThat(response.get().getProductName()).isNotNull(),
                () -> assertThat(response.get().getModel()).isNotNull(),
                () -> assertThat(response.get().getSupplierName()).isNotNull(),
                () -> assertThat(response.get().getPhone()).isNull(),
                () -> assertThat(response.get().getEmail()).isNotNull()
        );
    }

    @Test
    @DisplayName("Relation not found by product id and supplier id")
    void shouldReturnOptionalEmptyWhenFindByProductIdAndSupplierId() {
        // 1. Arrange
        Integer productId = 1, supplierId = 10;

        // 2. Act

        Optional<PSRelationResponseDto> response = adapter.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Get list of products by supplier id")
    void shouldReturnListOfProductsWhenFindBySupplierID() {
        // 1. Arrange
        Integer supplierId = 3;

        // 2. Act
        List<ProductByFindSupplierDto> response = adapter.productBySupplier(supplierId);

        // 3. Assert
        assertFalse(response.isEmpty());
    }

    @Test
    @DisplayName("Get list of suppliers by product id")
    @Sql(scripts = "/database/create_function_get_suppliers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(separator = "@@"))
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    void shouldReturnListOfSupplierWhenFindByProductId() {
        // 1. Arrange
        Integer productId = 6;

        // 2. Act
        Optional<SPSuppliersByFindProductDto> response = adapter.spSuppliersByProduct(productId);

        // 3. Assert
        assertTrue(response.isPresent());
        assertAll(
                () -> assertNotNull(response.get().getProductName()),
                () -> assertNotNull(response.get().getModel()),
                () -> assertNotNull(response.get().getSpecification()),
                () -> assertNotNull(response.get().getCategory()),
                () -> assertNotNull(response.get().getSymbol()),
                () -> assertNotNull(response.get().getSuppliers())
        );
        assertThat(response.get().getSuppliers().size()).isGreaterThan(0);
        assertAll(
                () -> assertNotNull(response.get().getSuppliers().get(0).getSupplierName()),
                () -> assertNotNull(response.get().getSuppliers().get(0).getPhone()),
                () -> assertNotNull(response.get().getSuppliers().get(0).getEmail()),
                () -> assertNotNull(response.get().getSuppliers().get(0).getPrice())
        );
    }

    @Test
    @DisplayName("Get list of suppliers empty by product id")
    @Sql(scripts = "/database/create_function_get_suppliers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(separator = "@@"))
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    void shouldReturnListOfSupplierEmptyWhenFindByProductId() {
        // 1. Arrange
        Integer productId = 7;

        // 2. Act
        Optional<SPSuppliersByFindProductDto> response = adapter.spSuppliersByProduct(productId);

        // 3. Assert
        assertTrue(response.isPresent());
        assertAll(
                () -> assertNotNull(response.get().getProductName()),
                () -> assertNotNull(response.get().getModel()),
                () -> assertNotNull(response.get().getSpecification()),
                () -> assertNotNull(response.get().getCategory()),
                () -> assertNotNull(response.get().getSymbol()),
                () -> assertNotNull(response.get().getSuppliers())
        );
        assertThat(response.get().getSuppliers().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Optional empty when get suppliers find by product id")
    @Sql(scripts = "/database/create_function_get_suppliers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(separator = "@@"))
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    void shouldReturnOptionalEmptyWhenGetSuppliersByProductId() {
        // 1. Arrange
        Integer productId = 20;

        // 2. Act
        Optional<SPSuppliersByFindProductDto> response = adapter.spSuppliersByProduct(productId);

        // 3. Assert
        assertTrue(response.isEmpty());
    }
}
