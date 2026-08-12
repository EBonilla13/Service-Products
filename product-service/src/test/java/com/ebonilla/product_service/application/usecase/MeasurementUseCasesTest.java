package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.IMeasurementPort;
import com.ebonilla.product_service.domain.model.Measurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementUseCasesTest {

    @Mock private IMeasurementPort measurementPort;

    @InjectMocks private MeasurementUseCases measurementUseCases;

    private MeasurementRequestDto request;
    private Measurement measurementSaved;

    @BeforeEach
    void setUp() {
        request = new MeasurementRequestDto();
        request.setUnit("mililitros");
        request.setSymbol("ml");

        measurementSaved = new Measurement();
        measurementSaved.setId(12);
        measurementSaved.setUnit("mililitros");
        measurementSaved.setSymbol("ml");
    }

    @Test
    @DisplayName("Create a measurement")
    void shouldCreateMeasurement(){
        // 1. Arrange
        when(measurementPort.create(any(Measurement.class))).thenReturn(measurementSaved);

        // 2. Act
        MeasurementResponseDto response = measurementUseCases.create(request);

        // 3. Assert
        assertNotNull(response);
        verify(measurementPort, times(1)).create(any());
    }

    @Test
    @DisplayName("No create a measurement")
    void shouldThrowExceptionByCreateMeasurement(){
        // 1. Arrange
        request.setSymbol("mililitross");

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> measurementUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(measurementPort, never()).create(any());
    }

    @Test
    @DisplayName("Update a measurement")
    void shouldUpdateMeasurement(){
        // 1. Arrange
        request.setId(20);
        when(measurementPort.update(any(Measurement.class))).thenReturn(measurementSaved);

        // 2. Act
        MeasurementResponseDto response = measurementUseCases.update(request);

        // 3. Assert
        assertNotNull(response);
        verify(measurementPort, times(1)).update(any());
    }

    @Test
    @DisplayName("No update a measurement")
    void shouldThrowExceptionByUpdateMeasurement(){
        // 1. Arrange
        request.setId(0);
        request.setUnit(null);
        request.setSymbol("ml");

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> measurementUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(measurementPort, never()).update(any());
    }

    @Test
    @DisplayName("No update by id null")
    void shouldThrowExceptionIdNull(){
        // 2. Act
        IdNullException exception = assertThrowsExactly( IdNullException.class,
                () -> measurementUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(measurementPort, never()).update(any());
    }

    @Test
    @DisplayName("Find by measurement Id")
    void shouldFindByMeasurementId(){
        // 1. Arrange
        Integer id = 23;
        MeasurementResponseDto responseDto = new MeasurementResponseDto(id, request.getUnit(), request.getSymbol(), null, null);
        when(measurementPort.findById(anyInt())).thenReturn(Optional.of(responseDto));
        // 2. Act
        MeasurementResponseDto response = measurementUseCases.findById(id);

        // 3. Assert
        assertNotNull(response);
        verify(measurementPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Not found by measurement Id")
    void shouldNotFoundByMeasurementId(){
        // 1. Arrange
        Integer id = 23;

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> measurementUseCases.findById(id));

        // 3. Assert
        assertNotNull(exception);
        verify(measurementPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("List of measurements")
    void shouldReturnListOfMeasurements(){
        // 1. Arrange
        MeasurementResponseDto responseDto1 = new MeasurementResponseDto(23, request.getUnit(), request.getSymbol(), null, null);
        MeasurementResponseDto responseDto2 = new MeasurementResponseDto(24, "litros", "L", null, null);
        List<MeasurementResponseDto> list = List.of(responseDto1, responseDto2);
        when(measurementPort.measurements()).thenReturn(list);

        // 2. Act
        List<MeasurementResponseDto> response = measurementUseCases.measurements();

        // 3. Assert
        assertNotNull(response);
        verify(measurementPort, times(1)).measurements();
    }

    @Test
    @DisplayName("Delete by measurement Id")
    void shouldDeleteByMeasurementId(){
        // 1. Arrange
        Integer id = 23;
        when(measurementPort.exists(anyInt())).thenReturn(true);
        doNothing().when(measurementPort).delete(anyInt());

        // 2. Act
        measurementUseCases.delete(id);

        // 3. Assert
        verify(measurementPort, times(1)).exists(anyInt());
        verify(measurementPort, times(1)).delete(anyInt());
    }

    @Test
    @DisplayName("Not delete by measurement Id")
    void shouldNotDeleteByMeasurementId(){
        // 1. Arrange
        Integer id = 23;
        when(measurementPort.exists(anyInt())).thenReturn(false);

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> measurementUseCases.delete(id));

        // 3. Assert
        assertNotNull(exception);
        verify(measurementPort, times(1)).exists(anyInt());
        verify(measurementPort, never()).delete(anyInt());
    }

}