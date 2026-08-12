package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.integration;

import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.domain.model.Product;
import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.ProductPersistenceAdapter;
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
        config = @SqlConfig( transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Sql(scripts = "/database/data_save.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        config = @SqlConfig( transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Import(ProductPersistenceAdapter.class)
class ProductPersistenceAdapterIntegrationTest extends BaseRepositoryTest {

    @Autowired private ProductPersistenceAdapter adapter;

    private Product productDomain;

    @Test
    @DisplayName("Create a product")
    void shouldCreateAProduct(){
        // 1. Arrange
        Integer categoryId = 2, measurementId = 2;
        String name = "laptop", model = "lenovo thinkpad", spec = "4 GB RAM 250 GB SSD";
        productDomain = Product.create(null, name, model, spec, categoryId, measurementId, new Notification());

        // 2. Act
        Product productSaved = adapter.create(productDomain);

        // 3. Assert
        assertNotNull(productSaved);
        assertAll(
                () -> assertNotNull(productSaved.getId()),
                () -> assertEquals(name, productSaved.getProductName()),
                () -> assertEquals(model, productSaved.getProductModel()),
                () -> assertEquals(spec, productSaved.getProductSpecification()),
                () -> assertEquals(categoryId, productSaved.getCategoryId()),
                () -> assertEquals(measurementId, productSaved.getMeasurementId())
        );
    }

    @Test
    @DisplayName("Update a product")
    void shouldUpdateAProduct() {
        // 1. Arrange
        Boolean existsProduct = adapter.exists(4);
        Integer id = existsProduct ? 4 : null, categoryId = 2, measurementId = 2;
        String name = "laptop", model = "lenovo thinkpad", spec = "4 GB RAM 250 GB SSD";
        productDomain = Product.create(id, name, model, spec, categoryId, measurementId, new Notification());

        // 2. Act
        Product productSaved = adapter.update(productDomain);

        // 3. Assert
        assertNotNull(productSaved);
        assertAll(
                () -> assertEquals(id, productSaved.getId()),
                () -> assertEquals(name, productSaved.getProductName()),
                () -> assertEquals(model, productSaved.getProductModel()),
                () -> assertEquals(spec, productSaved.getProductSpecification()),
                () -> assertEquals(categoryId, productSaved.getCategoryId()),
                () -> assertEquals(measurementId, productSaved.getMeasurementId())
        );
    }

    @Test
    @DisplayName("Update fail a product by not found product")
    void shouldThrowResourceNotFoundWhenUpdateAProduct() {
        // 1. Arrange
        Integer id = 10, categoryId = 2, measurementId = 2;
        String name = "laptop", model = "lenovo thinkpad", spec = "4 GB RAM 250 GB SSD";
        productDomain = Product.create(id, name, model, spec, categoryId, measurementId, new Notification());

        // 2. Act
        ResourceNotFoundException ex = assertThrowsExactly( ResourceNotFoundException.class,
                () -> adapter.update(productDomain));

        // 3. Assert
        assertNotNull(ex);
        assertThat(ex.getMessage()).contains(String.valueOf(id));
    }

    @Test
    @DisplayName("Product found by id")
    void shouldFindProductById() {
        // 1. Arrange
        Integer id = 2;

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Product not found by id")
    void shouldReturnEmptyOptionalWhenFindProductById() {
        // 1. Arrange
        Integer id = 20;

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Product found by name")
    void shouldFindProductByName() {
        // 1. Arrange
        String name = "playera";

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findByName(name);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Product not found by name")
    void shouldReturnEmptyOptionalWhenFindProductByName() {
        // 1. Arrange
        String name  = "laptop";

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findByName(name);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Product found by model")
    void shouldFindProductByModel() {
        // 1. Arrange
        String model = "iphone 14";

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findByModel(model);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getModel()).isEqualTo(model);
    }

    @Test
    @DisplayName("Product not found by model")
    void shouldReturnEmptyOptionalWhenFindProductByModel() {
        // 1. Arrange
        String model = "samsung";

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findByModel(model);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Product found by specification")
    void shouldFindProductBySpecification() {
        // 1. Arrange
        String specification = "12 mm";

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findBySpecification(specification);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getSpecification()).isEqualTo(specification);
    }

    @Test
    @DisplayName("Product not found by specification")
    void shouldReturnEmptyOptionalWhenFindProductBySpecification() {
        // 1. Arrange
        String specification = "42 pulgadas";

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findBySpecification(specification);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("List of products")
    void shouldListOfProducts() {
        // 2. Act
        List<ProductResponseDto> response = adapter.products();

        // 3. Assert
        assertFalse(response.isEmpty());
    }

    @Test
    @DisplayName("List of products by category id")
    void shouldReturnListOfProductsByCategoryId() {
        // 1. Arrange
        Integer categoryId = 3;

        // 2. Act
        List<ProductResponseDto> response = adapter.productsByCategoryId(categoryId);

        // 3. Assert
        assertFalse(response.isEmpty());
    }

    @Test
    @DisplayName("List of products empty by category id")
    void shouldReturnListOfProductsEmptyByCategoryId() {
        // 1. Arrange
        Integer categoryId = 30;

        // 2. Act
        List<ProductResponseDto> response = adapter.productsByCategoryId(categoryId);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Product exists when search by id")
    void shouldReturnTrueWhenFindById() {
        // 1. Arrange
        Integer id = 3;

        // 2. Act
        Boolean response = adapter.exists(id);

        // 3. Assert
        assertTrue(response);
    }

    @Test
    @DisplayName("Product doesn't exists when search by id")
    void shouldReturnFalseWhenFindById() {
        // 1. Arrange
        Integer id = 20;

        // 2. Act
        Optional<ProductResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isEmpty());
    }
}