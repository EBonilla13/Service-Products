package com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository;


import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Product;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.ProductSupplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/database/data_save.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class IProductSupplierIntegrationTest extends BaseRepositoryTest {

    @Autowired private IProductSupplier productSupplier;
    @Autowired private ISupplier iSupplier;
    @Autowired private IProduct iProduct;

    @Test
    @DisplayName("List product by productId and supplierId")
    void shouldReturnListOfProductsWithProductIdAndSupplierId(){
        // 1. Arrange
        Integer supplierId = null, productId = null;
        Optional<Supplier> supplier = iSupplier.findByName("elizondo");
        Optional<Product> product = iProduct.findByName("celular");
        if (supplier.isPresent() && product.isPresent()){
            supplierId = supplier.get().getId();
            productId = product.get().getId();
        }

        // 2. Act
        Optional<PSRelationResponseDto> response = productSupplier.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isPresent());
    }

    @Test
    @DisplayName("List empty by productId and supplierId without relation")
    void shouldReturnListEmptyWithProductIdAndSupplierIdWithoutRelation(){
        // 1. Arrange
        Integer supplierId = null, productId = null;
        Optional<Supplier> supplier = iSupplier.findByName("limpiatodo");
        Optional<Product> product = iProduct.findByName("celular");
        if (supplier.isPresent() && product.isPresent()){
            supplierId = supplier.get().getId();
            productId = product.get().getId();
        }

        // 2. Act
        Optional<PSRelationResponseDto> response = productSupplier.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("List empty by productId and supplierId not exists")
    void shouldReturnListEmptyWithProductIdAndSupplierNotExists(){
        // 1. Arrange
        Integer supplierId = null, productId = null;
        Optional<Supplier> supplier = iSupplier.findByName("soriana");
        Optional<Product> product = iProduct.findByName("celular");
        if (supplier.isPresent())
            supplierId = supplier.get().getId();
        else if (product.isPresent())
            productId = product.get().getId();

        // 2. Act
        Optional<PSRelationResponseDto> response = productSupplier.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("List product by supplierId")
    void shouldReturnListOfProducts(){
        // 1. Arrange
        Integer supplierId = 2;

        // 2. Act
        List<ProductSupplier> list = productSupplier.findBySupplierId(supplierId)
                .stream()
                .toList();

        // 3. Assert
        assertNotNull(list);
    }

    @Test
    @DisplayName("List empty by supplierId not exists")
    void shouldReturnListEmptyBySupplierId(){
        // 1. Arrange
        Integer supplierId = 100;

        // 2. Act
        List<ProductSupplier> list = productSupplier.findBySupplierId(supplierId)
                .stream()
                .toList();

        // 3. Assert
        assertTrue(list.isEmpty());
    }


}