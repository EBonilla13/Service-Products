package com.ebonilla.product_service.infrastructure.adapters.input.rest.integration.controller;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.BaseControllerTest;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.utils.JwtTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.json.JsonMapper;

import javax.crypto.SecretKey;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/database/data_save.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/database/data_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductControllerIntegrationTest extends BaseControllerTest {

    private final String BASE_PATH = "/api/v1/product";
    private JwtTestUtil jwtTestUtil;

    @Autowired private MockMvc mockMvc;
    @Autowired private SecretKey secretKey;
    @Autowired private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        this.jwtTestUtil = new JwtTestUtil(this.secretKey);
    }

    @Test
    @DisplayName("Create a new product with user role")
    void shouldCreateProductWithUserRole() throws Exception{
        // 1. Arrange
        ProductRequestDto request = new ProductRequestDto(null, "control inalambrico", "xbox series s", "bluetooth", 3, 3);
        String token = this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

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
    @DisplayName("Forbidden create a new product with invalid role")
    void shouldReturn403WhenCreateProductWithInvalidRole() throws Exception{
        // 1. Arrange
        ProductRequestDto request = new ProductRequestDto(null, "control inalambrico", "xbox series s", "bluetooth", 3, 3);
        String token = this.jwtTestUtil.generateJwtValid("employee45", List.of("employee"));

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
    @DisplayName("Validations error of product request dto when create")
    void shouldReturnValidationErrorWhenCreateProduct() throws Exception{
        // 1. Arrange
        ProductRequestDto request = new ProductRequestDto(null, null, "xbox series s", null, -1, null);
        String token = this.jwtTestUtil.generateJwtValid("employee45", List.of("employee"));

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
    @DisplayName("Update a product with admin role")
    void shouldUpdateAProductWithAdminRole() throws Exception{
        // 1. Arrange
        ProductRequestDto request = new ProductRequestDto(3, "trapeador", "clean", "cerdas finas", 4, 3);
        String token = this.jwtTestUtil.generateJwtValid("admin98", List.of("admin"));

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
    @DisplayName("Forbidden update a product with invalid role")
    void shouldReturn403WhenUpdateProductWithInvalidRole() throws Exception{
        // 1. Arrange
        ProductRequestDto request = new ProductRequestDto(3, "trapeador", "clean", "cerdas finas", 4, 3);
        String token = this.jwtTestUtil.generateJwtValid("employee2", List.of("employee", "product:write"));

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
    @DisplayName("Validation error by product request dto data")
    void shouldReturnValidationErrorByRequestWhenUpdate() throws Exception{
        // 1. Arrange
        ProductRequestDto request = new ProductRequestDto(3, "trapeador", null, "cerdas finas", 4, 3);
        String token = this.jwtTestUtil.generateJwtValid("user45", List.of("user"));

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
    @DisplayName("Get product by Id with valid role")
    void shouldFindProductByIdWithUserRole() throws Exception{
        // 1. Arrange
        Integer id = 3;
        String token = this.jwtTestUtil.generateJwtValid("employee34", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .header("Authorization", "Bearer " + token)
                        .param("id", String.valueOf(id)))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get product by id with invalid role")
    void shouldReturn403WhenFindProductByIdWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer id = 1;
        String token = this.jwtTestUtil.generateJwtValid("visit", List.of("visit", "product:write"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .header("Authorization", "Bearer " + token)
                        .param("id", String.valueOf(id)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by param invalid")
    void shouldReturnValidationErrorByInvalidId() throws Exception{
        // 1. Arrange
        String id = "a";
        String token = this.jwtTestUtil.generateJwtValid("user34", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .header("Authorization", "Bearer " + token)
                        .param("id", id))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get product by Name with valid role")
    void shouldFindProductByNameWithUserRole() throws Exception{
        // 1. Arrange
        String name = "escoba";
        String token = this.jwtTestUtil.generateJwtValid("admin2", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/name")
                        .header("Authorization", "Bearer " + token)
                        .param("name", name))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get product by Name with invalid role")
    void shouldReturn403WhenFindProductByNameWithInvalidRole() throws Exception{
        // 1. Arrange
        String name = "trapeador";
        String token = this.jwtTestUtil.generateJwtValid("invalid", List.of("product:read"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/name")
                        .header("Authorization", "Bearer " + token)
                        .param("name", name))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by param name invalid")
    void shouldReturnValidationErrorByInvalidName() throws Exception{
        // 1. Arrange
        String name = "";
        String token = this.jwtTestUtil.generateJwtValid("employee24", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/name")
                        .header("Authorization", "Bearer " + token)
                        .param("name", name))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get product by Model with valid role")
    void shouldFindProductByModelWithUserRole() throws Exception{
        // 1. Arrange
        String model = "iphone 14";
        String token = this.jwtTestUtil.generateJwtValid("admin21", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/model")
                        .header("Authorization", "Bearer " + token)
                        .param("model", model))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get product by Model with invalid role")
    void should403WhenFindProductByModelWithInvalidRole() throws Exception{
        // 1. Arrange
        String model = "mabe";
        String token = this.jwtTestUtil.generateJwtValid("invalid", List.of("invalid"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/model")
                        .header("Authorization", "Bearer " + token)
                        .param("model", model))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by param invalid model")
    void shouldValidationErrorByInvalidModel() throws Exception{
        // 1. Arrange
        String model = null;
        String token = this.jwtTestUtil.generateJwtValid("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/model")
                        .header("Authorization", "Bearer " + token)
                        .param("model", model))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get product by specification with valid role")
    void shouldFindProductBySpecificationWithUserRole() throws Exception{
        // 1. Arrange
        String specification = "12 mm";
        String token = this.jwtTestUtil.generateJwtValid("admin21", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/specification")
                        .header("Authorization", "Bearer " + token)
                        .param("specification", specification))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get product by specification with invalid role")
    void shouldReturn403WhenFindBySpecificationWithInvalidRole() throws Exception{
        // 1. Arrange
        String specification = "16 gb";
        String token = this.jwtTestUtil.generateJwtValid("visit", List.of("visit"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/specification")
                        .header("Authorization", "Bearer " + token)
                        .param("specification", specification))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by param invalid specification")
    void shouldValidationErrorByInvalidSpecification() throws Exception{
        // 1. Arrange
        String specification = "";
        String token = this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/specification")
                        .header("Authorization", "Bearer " + token)
                        .param("specification", specification))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Show all products with valid role")
    void shouldShowAllProductWithValidRole() throws Exception{
        // 1. Arrange
        String token = this.jwtTestUtil.generateJwtValid("admin1", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden show all products with invalid role")
    void shouldReturn403WhenShowWithInvalidRole() throws Exception{
        // 1. Arrange
        String token = this.jwtTestUtil.generateJwtValid("other", List.of("other"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Show all products with category id and valid role")
    void shouldShowAllProductWithCategoryIdAndValidRole() throws Exception{
        // 1. Arrange
        Integer categoryId = 3;
        String token = this.jwtTestUtil.generateJwtValid("user4", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show/categoryId")
                        .header("Authorization", "Bearer " + token)
                        .param("categoryId", String.valueOf(categoryId)))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden show all products with category id and valid role")
    void shouldReturn403WhenShowAllProductWithCategoryIdAndValidRole() throws Exception{
        // 1. Arrange
        Integer categoryId = 3;
        String token = this.jwtTestUtil.generateJwtValid("wrong", List.of("wrong"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show/categoryId")
                        .header("Authorization", "Bearer " + token)
                        .param("categoryId", String.valueOf(categoryId)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by param invalid category id")
    void shouldReturnValidationErrorByInvalidCategoryId() throws Exception{
        // 1. Arrange
        Integer categoryId = null;
        String token = this.jwtTestUtil.generateJwtValid("user23", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show/categoryId")
                        .header("Authorization", "Bearer " + token)
                        .param("categoryId", String.valueOf(categoryId)))
                .andReturn();

        // 3. Assert
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(400, result.getResponse().getStatus());
    }
}