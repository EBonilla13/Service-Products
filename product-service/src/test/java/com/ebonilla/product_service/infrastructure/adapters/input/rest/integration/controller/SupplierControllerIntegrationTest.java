package com.ebonilla.product_service.infrastructure.adapters.input.rest.integration.controller;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
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
class SupplierControllerIntegrationTest extends BaseControllerTest {

    private final String BASE_PATH = "/api/v1/supplier";
    private JwtTestUtil jwtTestUtil;

    @Autowired private MockMvc mockMvc;
    @Autowired private SecretKey secretKey;
    @Autowired private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        this.jwtTestUtil = new JwtTestUtil(this.secretKey);
    }

    @Test
    @DisplayName("Create a supplier with valid role")
    void shouldCreateSupplierWithValidRole() throws Exception{
        // 1. Arrange
        SupplierRequestDto request = new SupplierRequestDto(null, "refaccionaria las torres", "8129392019", null);
        String token = this.jwtTestUtil.generateJwtValid("userTemp12", List.of("supplier:write"));

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
    @DisplayName("Forbidden create a supplier with invalid role")
    void shouldReturn403WhenCreateSupplierWithInvalidRole() throws Exception{
        // 1. Arrange
        SupplierRequestDto request = new SupplierRequestDto(null, "refaccionaria las torres", "8129392019", "correo-valido@gmail.com");
        String token = this.jwtTestUtil.generateJwtValid("userTemp12", List.of("temporal"));

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
    @DisplayName("Validation error by supplier request dto data")
    void shouldReturnValidationErrorByRequestSupplierDto() throws Exception{
        // 1. Arrange
        SupplierRequestDto request = new SupplierRequestDto(null, "refaccionaria las torres", "812939", null);
        String token = this.jwtTestUtil.generateJwtValid("admin2", List.of("admin"));

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
    @DisplayName("Update a supplier with valid role")
    void shouldUpdateSupplierWithValidRole() throws Exception{
        // 1. Arrange
        SupplierRequestDto request = new SupplierRequestDto(2, "bodega aurrera", "8129392019", "aurrera-nl-mty@hotmail.com");
        String token = this.jwtTestUtil.generateJwtValid("user6", List.of("user"));

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
    @DisplayName("Forbidden update a supplier with invalid role")
    void shouldReturn403WhenUpdateSupplierWithInvalidRole() throws Exception{
        // 1. Arrange
        SupplierRequestDto request = new SupplierRequestDto(3, "costco", "8129234519", "costco-buenavista@hotmail.com");
        String token = this.jwtTestUtil.generateJwtValid("employee5", List.of("employee"));

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
    @DisplayName("Validation error by request when update")
    void shouldReturnValidationErrorWhenUpdate() throws Exception{
        // 1. Arrange
        SupplierRequestDto request = new SupplierRequestDto(2, null, "8129392019", "aurrera-nl-mty@hotmail.com");
        String token = this.jwtTestUtil.generateJwtValid("user6", List.of("user"));

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
    @DisplayName("Get a supplier by id with valid role")
    void shouldFindSupplierByIdWithValidRole() throws Exception{
        // 1. Arrange
        Integer id = 3;
        String token = this.jwtTestUtil.generateJwtValid("user6", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get a supplier by id with invalid role")
    void shouldReturn403WhenFindSupplierByIdWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer id = 2;
        String token = this.jwtTestUtil.generateJwtValid("employee12", List.of("employee", "supplier:read"));

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
        Integer id = 3;
        String token = this.jwtTestUtil.generateJwtValid("user6", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get a supplier by name with valid role")
    void shouldFindSupplierByNameWithValidRole() throws Exception{
        // 1. Arrange
        String name = "elizondo";
        String token = this.jwtTestUtil.generateJwtValid("admin34", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/name")
                        .param("name", name)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get a supplier by name with valid role")
    void shouldReturn403WhenFindSupplierByNameWithValidRole() throws Exception{
        // 1. Arrange
        String name = "elektra";
        String token = this.jwtTestUtil.generateJwtValid("employee", List.of("supplier:read"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/name")
                        .param("name", name)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by invalid param name")
    void shouldReturnValidationErrorByInvalidParamName() throws Exception{
        // 1. Arrange
        String name = "";
        String token = this.jwtTestUtil.generateJwtValid("user12", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/name")
                        .param("name", name)
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get all suppliers with valid role")
    void shouldFindAllSuppliersWithValidRole() throws Exception{
        // 1. Arrange
        String token = this.jwtTestUtil.generateJwtValid("admin2", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get all suppliers with valid role")
    void shouldReturn403WhenFindAllSuppliersWithInvalidRole() throws Exception{
        // 1. Arrange
        String token = this.jwtTestUtil.generateJwtValid("username23", List.of("other"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + token))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }
}