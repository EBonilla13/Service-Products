package com.ebonilla.product_service.infrastructure.adapters.input.rest.integration.controller;

import com.ebonilla.product_service.application.dto.productsupplier.request.*;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.BaseControllerTest;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.utils.JwtTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.json.JsonMapper;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/database/data_save.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductSupplierControllerIntegrationTest extends BaseControllerTest {

    private final String BASE_PATH = "/api/v1/product-supplier";
    private JwtTestUtil jwtTestUtil;

    @Autowired private MockMvc mockMvc;
    @Autowired private SecretKey secretKey;
    @Autowired private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        this.jwtTestUtil = new JwtTestUtil(this.secretKey);
    }

    @Test
    @DisplayName("Create relation with valid role")
    void shouldCreateARelationWithValidRole() throws Exception{
        // 1. Arrange
        ProductSupplierRequestDto request =  new ProductSupplierRequestDto(null, 1, 4, new BigDecimal("5999.49"));
        String token =  this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden create relation with invalid role")
    void shouldReturn403WhenCreateARelationWithInvalidRole() throws Exception{
        // 1. Arrange
        ProductSupplierRequestDto request =  new ProductSupplierRequestDto(null, 1, 4, new BigDecimal("5999.49"));
        String token =  this.jwtTestUtil.generateJwtValid("employee12", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by relation request dto when create")
    void shouldReturnValidationErrorWhenCreateARelation() throws Exception{
        // 1. Arrange
        ProductSupplierRequestDto request =  new ProductSupplierRequestDto(null, 1, 4, null);
        String token =  this.jwtTestUtil.generateJwtValid("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Create product, supplier and relation with valid role")
    void shouldCreateAllRelationWithValidRole() throws Exception{
        // 1. Arrange
        Product product = new Product(null, "ventilador", "myair", "51 cm 3 velocidades", 3, 3);
        Supplier supplier = new Supplier(null, "merco", "8212932918", null);
        RelationRequestDto relation = new RelationRequestDto(new BigDecimal("3299.59"));
        PSRelationRequestDto request = new PSRelationRequestDto(product, supplier, relation);
        String token =  this.jwtTestUtil.generateJwtValid("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH + "/relation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden create product, supplier and relation with valid role")
    void shouldReturn403WhenCreateAllRelationWithInvalidRole() throws Exception{
        // 1. Arrange
        Product product = new Product(null, "ventilador", "myair", "51 cm 3 velocidades", 3, 3);
        Supplier supplier = new Supplier(null, "merco", "8212932918", null);
        RelationRequestDto relation = new RelationRequestDto(new BigDecimal("3299.59"));
        PSRelationRequestDto request = new PSRelationRequestDto(product, supplier, relation);

        String token =  this.jwtTestUtil.generateJwtValid("employee12", List.of("employee", "product-supplier:write"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH + "/relation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error when create product, supplier and relation")
    void shouldReturnValidationErrorWhenCreateAllRelation() throws Exception{
        // 1. Arrange
        Product product = new Product(null, "ventilador", "myair", null, 3, 3);
        Supplier supplier = new Supplier(null, "merco", "821212932918", null);
        RelationRequestDto relation = new RelationRequestDto(null);
        PSRelationRequestDto request = new PSRelationRequestDto(product, supplier, relation);
        String token =  this.jwtTestUtil.generateJwtValid("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH + "/relation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Update a relation with valid role")
    void shouldUpdateARelationWithValidRole() throws Exception{
        // 1. Arrange
        ProductSupplierRequestDto request = new ProductSupplierRequestDto(2, 1, 3, new BigDecimal("5299.99"));
        String token =  this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(put(BASE_PATH)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden update a relation with invalid role")
    void shouldReturn403WhenUpdateARelationWithInvalidRole() throws Exception{
        // 1. Arrange
        ProductSupplierRequestDto request = new ProductSupplierRequestDto(2, 1, 3, new BigDecimal("5299.99"));
        String token =  this.jwtTestUtil.generateJwtValid("userTemp", List.of("temp", "product-supplier:write"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(put(BASE_PATH)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error when update a relation")
    void shouldReturnValidationErrorWhenUpdateRelation() throws Exception{
        // 1. Arrange
        ProductSupplierRequestDto request = new ProductSupplierRequestDto(2, null, 3, null);
        String token =  this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(put(BASE_PATH)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get a relation by id with valid role")
    void shouldFindRelationByIdWithValidRole() throws Exception{
        // 1. Arrange
        Integer id = 5;
        String token =  this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get a relation by id with invalid role")
    void shouldReturn403WhenFindRelationByIdWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer id = 5;
        String token =  this.jwtTestUtil.generateJwtValid("username", List.of("other"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by invalid param id")
    void shouldReturnValidationErrorByInvalidParamId() throws Exception{
        // 1. Arrange
        Integer id = -10;
        String token =  this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get a relation by product id and supplier id with valid role")
    void shouldFindRelationByProductIdAndSupplierIdWithValidRole() throws Exception{
        // 1. Arrange
        Integer productId = 1, supplierId = 3;
        String token =  this.jwtTestUtil.generateJwtValid("admin6", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/relation")
                        .param("productId", String.valueOf(productId))
                        .param("supplierId", String.valueOf(supplierId))
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get a relation by product id and supplier id with invalid role")
    void shouldReturn403WhenFindRelationByProductIdAndSupplierIdWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer productId = 2, supplierId = 3;
        String token =  this.jwtTestUtil.generateJwtValid("employee2", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/relation")
                        .param("productId", String.valueOf(productId))
                        .param("supplierId", String.valueOf(supplierId))
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by invalid params id")
    void shouldReturnValidationErrorByInvalidParams() throws Exception{
        // 1. Arrange
        Integer productId = -200, supplierId = null;
        String token =  this.jwtTestUtil.generateJwtValid("admin6", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/relation")
                        .param("productId", String.valueOf(productId))
                        .param("supplierId", String.valueOf(supplierId))
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get a suppliers by product id with valid role")
    @Sql(scripts = "/database/create_function_get_suppliers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            config = @SqlConfig(separator = "@@"))
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    void shouldFindSuppliersByProductIdWithValidRole() throws Exception{
        // 1. Arrange
        Integer productId = 1;
        String token =  this.jwtTestUtil.generateJwtValid("admin6", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/supplier/{productId}", productId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get a suppliers by product id with invalid role")
    void shouldReturn403WhenFindSuppliersByProductIdWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer productId = 1;
        String token =  this.jwtTestUtil.generateJwtValid("employee12", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/supplier/{productId}", productId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by invalid param product id")
    void shouldReturnInvalidErrorByInvalidParamProductId() throws Exception{
        // 1. Arrange
        Integer productId = -12;
        String token =  this.jwtTestUtil.generateJwtValid("admin1", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/supplier/{productId}", productId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get a products by supplier id with valid role")
    void shouldGetProductsBySupplierIdWithValidRole() throws Exception{
        // 1. Arrange
        Integer supplierId = 4;
        String token =  this.jwtTestUtil.generateJwtValid("user9", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/products/{supplierId}", supplierId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get a products by supplier id with invalid role")
    void shouldReturn403WhenFindProductsBySupplierIdWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer supplierId = 4;
        String token =  this.jwtTestUtil.generateJwtValid("username", List.of("other"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/products/{supplierId}", supplierId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by invalid param supplier id")
    void shouldReturnValidationErrorByInvalidParamSupplierId() throws Exception{
        // 1. Arrange
        Integer supplierId = -2;
        String token =  this.jwtTestUtil.generateJwtValid("user9", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/products/{supplierId}", supplierId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }
}