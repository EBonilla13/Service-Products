package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.integration;

import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.domain.model.Measurement;
import com.ebonilla.product_service.domain.validation.Notification;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.BaseRepositoryTest;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.MeasurementPersistenceAdapter;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IMeasurement;
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
@Sql(scripts = "/database/data_measurement.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, 
        config = @SqlConfig( transactionMode = SqlConfig.TransactionMode.ISOLATED))
@Import(MeasurementPersistenceAdapter.class)
class MeasurementPersistenceAdapterIntegrationTest extends BaseRepositoryTest {

    @Autowired private MeasurementPersistenceAdapter adapter;
    @Autowired private IMeasurement repository;
    
    private Measurement measurementDomain;
    
    @Test
    @DisplayName("Create a measurement")
    void shouldCreateAMeasurement(){
        // 1. Arrange
        String unit = "mililitros", symbol = "ml";
        measurementDomain = Measurement.create(null, unit, symbol, new Notification());
        
        // 2. Act
        Measurement measurementSaved = adapter.create(measurementDomain);
        
        // 3. Assert
        assertNotNull(measurementSaved);
        assertAll(
                () -> assertThat(measurementSaved.getId()).isGreaterThan(0),
                () -> assertEquals(unit, measurementSaved.getUnit()),
                () -> assertEquals(symbol, measurementSaved.getSymbol())
        );
    }

    @Test
    @DisplayName("Update a measurement")
    void shouldUpdateAMeasurement(){
        // 1. Arrange
        String unit = "metro", symbol = "m";
        Optional<MeasurementResponseDto> response = adapter.findById(3);

        if (response.isEmpty())
            System.out.println("Measurement not found, test fail.");

        measurementDomain = Measurement.create(response.map(MeasurementResponseDto::getId).orElse(null), unit, symbol, new Notification());

        // 2. Act
        Measurement measurementSaved = adapter.update(measurementDomain);

        // 3. Assert
        assertNotNull(measurementSaved.getId());
        assertAll(
                () -> assertEquals(3, measurementSaved.getId()),
                () -> assertEquals(unit, measurementSaved.getUnit()),
                () -> assertEquals(symbol, measurementSaved.getSymbol())
        );
    }

    @Test
    @DisplayName("No Update a measurement by resource not found")
    void shouldThrowResourceNotFoundWhenUpdate(){
        // 1. Arrange
        String unit = "metro", symbol = "m";
        Integer id = 100;

        measurementDomain = Measurement.create(id, unit, symbol, new Notification());

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> adapter.update(measurementDomain));

        // 3. Assert
        assertNotNull(exception);
        assertThat(exception.getMessage()).contains(String.valueOf(id));
    }

    @Test
    @DisplayName("Measurement found by id")
    void shouldGetMeasurementById(){
        // 1. Arrange
        Integer id = 3;


        // 2. Act
        Optional<MeasurementResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Measurement not found by id")
    void shouldReturnOptionalEmptyWhenFindById(){
        // 1. Arrange
        Integer id = 10;


        // 2. Act
        Optional<MeasurementResponseDto> response = adapter.findById(id);

        // 3. Assert
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("List of measurement")
    void shouldGetListOfMeasurement(){

        // 2. Act
        List<MeasurementResponseDto> response = adapter.measurements();

        // 3. Assert
        assertFalse(response.isEmpty());
        assertThat(response.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("Exists measurement")
    void shouldReturnTrueWhenGetById(){
        // 1. Assert
        Integer id = 2;

        // 2. Act
        Boolean response = adapter.exists(id);

        // 3. Assert
        assertTrue(response);
    }

    @Test
    @DisplayName("No exists measurement")
    void shouldReturnFalseWhenGetById(){
        // 1. Assert
        Integer id = 20;

        // 2. Act
        Boolean response = adapter.exists(id);

        // 3. Assert
        assertFalse(response);
    }

    @Test
    @DisplayName("Delete measurement by id")
    void shouldDeleteMeasurementById(){
        // 1. Assert
        Integer measurementId = 4;
        Boolean exists = adapter.exists(measurementId);

        assertTrue(exists);

        // 2. Act
        adapter.delete(measurementId);

        exists = adapter.exists(measurementId);

        // 3. Assert
        assertFalse(exists);
    }
}