package com.ebonilla.product_service.infrastructure.adapters.input.rest.integration.controller;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
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
class CategoryControllerIntegrationTest extends BaseControllerTest {

    private final String BASE_PATH = "/api/v1/category";
    private JwtTestUtil jwtTestUtil;

    @Autowired private MockMvc mockMvc;
    @Autowired private SecretKey secretKey;
    @Autowired private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        this.jwtTestUtil = new JwtTestUtil(secretKey);
    }

    @Test
    @DisplayName("401 by invalid token")
    void shouldResponseUnauthorizedByInvalidToken() throws Exception{
        // 1. Arrange
        String invalidToken = this.jwtTestUtil.generatedTokenWithInvalidSecretKey("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(401, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("403 by role invalid")
    void shouldResponseForbiddenByInvalidRole() throws Exception{
        // 1. Arrange
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee34", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Create a new category with role user")
    void shouldCreateCategoryWithRoleUser() throws Exception{
        // 1. Arrange
        CategoryRequestDto request = new CategoryRequestDto(null, "electronica");
        String invalidToken = this.jwtTestUtil.generateJwtValid("user13", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden create a category by invalid role")
    void should403WhenCreateACategoryWithInvalidRole() throws Exception{
        // 1. Arrange
        CategoryRequestDto request = new CategoryRequestDto(null, "ferreteria");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee100", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by data of dto")
    void shouldReturnValidationErrorWhenCreateACategory() throws Exception{
        // 1. Arrange
        CategoryRequestDto request = new CategoryRequestDto(null, "");
        String invalidToken = this.jwtTestUtil.generateJwtValid("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(post(BASE_PATH)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Update a category with authority write")
    void shouldUpdateCategoryWithRoleAdmin() throws Exception{
        // 1. Arrange
        CategoryRequestDto request = new CategoryRequestDto(2, "aceites");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee43", List.of("employee","category:write"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(put(BASE_PATH)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden update a category by invalid role")
    void should403WhenUpdateACategoryWithInvalidRole() throws Exception{
        // 1. Arrange
        CategoryRequestDto request = new CategoryRequestDto(4, "ferreteria");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee100", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(put(BASE_PATH)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by data of dto")
    void shouldReturnValidationErrorWhenUpdateACategory() throws Exception{
        // 1. Arrange
        CategoryRequestDto request = new CategoryRequestDto(5, "");
        String invalidToken = this.jwtTestUtil.generateJwtValid("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(put(BASE_PATH)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get category by ID with role user")
    void shouldFindCategoryByIdWithRoleUser() throws Exception{
        // 1. Arrange
        Integer idCategory = 4;
        String token = this.jwtTestUtil.generateJwtValid("user90", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .param("idCategory", String.valueOf(idCategory))
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get category by ID with role employee")
    void should403WhenFindCategoryByIdWithRoleUser() throws Exception{
        // 1. Arrange
        Integer idCategory = 4;
        String token = this.jwtTestUtil.generateJwtValid("employee16", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .param("idCategory", String.valueOf(idCategory))
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validations by ID are letters")
    void shouldReturnValidationErrorByIdAreLetters() throws Exception{
        // 1. Arrange
        String wrongId = "abc";
        String token = this.jwtTestUtil.generateJwtValid("user90", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .param("idCategory", wrongId)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get all categories")
    void shouldFindAllCategories() throws Exception{
        // 1. Arrange
        String token = this.jwtTestUtil.generateJwtValid("user90", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }
}