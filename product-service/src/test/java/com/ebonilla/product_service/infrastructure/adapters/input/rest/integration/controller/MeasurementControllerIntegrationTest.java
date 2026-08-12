package com.ebonilla.product_service.infrastructure.adapters.input.rest.integration.controller;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
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
class MeasurementControllerIntegrationTest extends BaseControllerTest {

    private final String BASE_PATH = "/api/v1/measurement";
    private JwtTestUtil jwtTestUtil;

    @Autowired private MockMvc mockMvc;
    @Autowired private SecretKey secretKey;
    @Autowired private JsonMapper mapper;

    @BeforeEach
    void setUp() {
        this.jwtTestUtil = new JwtTestUtil(secretKey);
    }

    @Test
    @DisplayName("Unauthorized by invalid token")
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
    @DisplayName("Forbidden create measurement with invalid role")
    void shouldReturn403ForbiddenByInvalidRoleWhenCreateMeasurement() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(null, "gramos", "gr");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee1", List.of("employee"));

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
    @DisplayName("401 Unauthorized by token expired")
    void shouldReturn401UnauthorizedByTokenExpired() throws Exception{
        // 1. Arrange
        Integer id = 3;
        String invalidToken = this.jwtTestUtil.generatedTokenExpired("admin12", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id")
                        .param("id", String.valueOf(id))
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(401, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Create a measurement with authority write")
    void shouldCreateAMeasurementWithAuthorityWrite() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(null, "gramos", "gr");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee43", List.of("employee", "measurement:write"));

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
    @DisplayName("Forbidden create a measurement with role employee")
    void shouldReturn403WhenCreateAMeasurementWithEmployeeRole() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(null, "tonelada", "ton");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee43", List.of("employee"));

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
    @DisplayName("Validation error by request dto")
    void shouldReturnValidationErrorByMeasurementRequestDto() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(null, "tonelada", "");
        String invalidToken = this.jwtTestUtil.generateJwtValid("user63", List.of("user"));

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
    @DisplayName("Update a measurement with admin role")
    void shouldUpdateAMeasurementWithAdminRole() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(2, "mililitros", "ml");
        String invalidToken = this.jwtTestUtil.generateJwtValid("admin3", List.of("admin"));

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
    @DisplayName("Forbidden update with employee role")
    void should403WhenUpdateWithEmployeeRole() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(2, "par", "par");
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee13", List.of("employee"));

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
    @DisplayName("Validation error of measurement request dto")
    void shouldReturnValidationErrorWhenUpdateByRequestDto() throws Exception{
        // 1. Arrange
        MeasurementRequestDto request = new MeasurementRequestDto(2, "", null);
        String invalidToken = this.jwtTestUtil.generateJwtValid("admin3", List.of("admin"));

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
    @DisplayName("Get measurement by id with user role")
    void shouldFindMeasurementByIdWithUserRole() throws Exception{
        // 1. Arrange
        Integer id = 1;
        String invalidToken = this.jwtTestUtil.generateJwtValid("User1", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get measurement by id with employee role")
    void shouldReturn403WhenFindByIdWithEmployeeRole() throws Exception{
        // 1. Arrange
        Integer id = 2;
        String invalidToken = this.jwtTestUtil.generateJwtValid("Employee14", List.of("employee"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Validation error by request measurement dto")
    void shouldReturnValidationErrorByRequestMeasurementDto() throws Exception{
        // 1. Arrange
        Integer id = -10;
        String invalidToken = this.jwtTestUtil.generateJwtValid("Admin45", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/id/{id}", id)
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Get all measurements with a valid role")
    void shouldFindAllMeasurementWithUserRole() throws Exception{
        // 1. Arrange
        String invalidToken = this.jwtTestUtil.generateJwtValid("user13", List.of("user"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidden get all measurements with invalid role")
    void shouldReturn403WhenFindAllMeasurementWithInvalidRole() throws Exception{
        // 1. Arrange
        String invalidToken = this.jwtTestUtil.generateJwtValid("employee12", List.of("employee", "measurement:read"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(get(BASE_PATH + "/show")
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Delete a measurement with a valid role")
    void shouldDeleteAMeasurementWithAdminRole() throws Exception{
        // 1. Arrange
        Integer id = 5;
        String invalidToken = this.jwtTestUtil.generateJwtValid("admin34", List.of("admin"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(delete(BASE_PATH + "/id")
                        .param("id", String.valueOf(id))
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Forbidde delete a measurement with a valid role")
    void shouldReturn403WhenDeleteAMeasurementWithInvalidRole() throws Exception{
        // 1. Arrange
        Integer id = 2;
        String invalidToken = this.jwtTestUtil.generateJwtValid("user89", List.of("user", "measurement:delete"));

        // 2. Act
        MvcResult result = this.mockMvc.perform(delete(BASE_PATH + "/id")
                        .param("id", String.valueOf(id))
                        .header("Authorization", "Bearer " + invalidToken))
                .andReturn();

        // 3. Assert
        assertEquals(403, result.getResponse().getStatus());
    }
}