package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.ISupplierPort;
import com.ebonilla.product_service.domain.model.Supplier;
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
class SupplierUseCasesTest {

    @Mock private ISupplierPort supplierPort;

    @InjectMocks private SupplierUseCases supplierUseCases;

    private SupplierRequestDto request;
    private Supplier supplierSaved;

    @BeforeEach
    void setUp() {
        request = new SupplierRequestDto();
        request.setName("autozone");
        request.setPhone("8121121389");
        request.setEmail("autozone@gmail.com");

        supplierSaved = new Supplier();
        supplierSaved.setId(10);
        supplierSaved.setSupplierName("autozone");
        supplierSaved.setNumberPhone("8121121389");
        supplierSaved.setEmail("autozone@gmail.com");
    }

    @Test
    @DisplayName("create a supplier")
    void shouldCreateASupplier(){
        // 1. Arrange
        when(supplierPort.create(any(Supplier.class))).thenReturn(supplierSaved);

        // 2. Act
        SupplierResponseDto response = supplierUseCases.create(request);

        // 3. Assert
        assertNotNull(response);
        verify(supplierPort, times(1)).create(any());
    }

    @Test
    @DisplayName("not create a supplier")
    void shouldThrowExceptionWhenCreateASupplier(){
        // 1. Arrange
        request.setPhone("81213412");
        request.setName(null);

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> supplierUseCases.create(request));

        // 3. Assert
        assertNotNull(exception);
        verify(supplierPort, never()).create(any());
    }

    @Test
    @DisplayName("update a supplier")
    void shouldUpdateASupplier(){
        // 1. Arrange
        request.setId(10);
        when(supplierPort.update(any(Supplier.class))).thenReturn(supplierSaved);

        // 2. Act
        SupplierResponseDto response = supplierUseCases.update(request);

        // 3. Assert
        assertNotNull(response);
        verify(supplierPort, times(1)).update(any());
    }

    @Test
    @DisplayName("not update a supplier by id null")
    void shouldThrowIdNullExceptionWhenCreateAUpdate(){
        // 1. Arrange
        request.setId(null);

        // 2. Act
        IdNullException exception = assertThrowsExactly( IdNullException.class,
                () -> supplierUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(supplierPort, never()).update(any());
    }

    @Test
    @DisplayName("not update a supplier")
    void shouldThrowBusinessLogicExceptionWhenCreateAUpdate(){
        // 1. Arrange
        request.setId(0);
        request.setEmail("autozone.gmail.com");

        // 2. Act
        BusinessLogicException exception = assertThrowsExactly( BusinessLogicException.class,
                () -> supplierUseCases.update(request));

        // 3. Assert
        assertNotNull(exception);
        verify(supplierPort, never()).update(any());
    }

    @Test
    @DisplayName("supplier found by id")
    void shouldFindById(){
        // 1. Arrange
        Integer id = 10;
        SupplierResponseDto responseDto = new SupplierResponseDto(id, request.getName(), request.getPhone(), request.getEmail());
        when(supplierPort.findById(anyInt())).thenReturn(Optional.of(responseDto));

        // 2. Act
        SupplierResponseDto response = supplierUseCases.findById(id);

        // 3. Assert
        assertNotNull(response);
        verify(supplierPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("supplier not found by id")
    void shouldThrowResourceNotFoundWhenFindById(){
        // 1. Arrange
        Integer id = 100;

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> supplierUseCases.findById(id));

        // 3. Assert
        assertNotNull(exception);
        verify(supplierPort, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("supplier found by name")
    void shouldFindByName(){
        // 1. Arrange
        String name = "autozone";
        SupplierResponseDto responseDto = new SupplierResponseDto(10, request.getName(), request.getPhone(), request.getEmail());
        when(supplierPort.findByName(anyString())).thenReturn(Optional.of(responseDto));

        // 2. Act
        SupplierResponseDto response = supplierUseCases.findByName(name);

        // 3. Assert
        assertNotNull(response);
        verify(supplierPort, times(1)).findByName(name);
    }

    @Test
    @DisplayName("supplier not found by name")
    void shouldThrowResourceNotFoundWhenFindByName(){
        // 1. Arrange
        String name = "refaccionaria lopez";

        // 2. Act
        ResourceNotFoundException exception = assertThrowsExactly( ResourceNotFoundException.class,
                () -> supplierUseCases.findByName(name));

        // 3. Assert
        assertNotNull(exception);
        verify(supplierPort, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("List of suppliers")
    void shouldReturnListOfSuppliers(){
        // 1. Arrange
        SupplierResponseDto responseDto1 = new SupplierResponseDto(11, "autopartes guzman", "8123452312", "auto_partesguz@hotmail.com");
        SupplierResponseDto responseDto2 = new SupplierResponseDto(10, request.getName(), request.getPhone(), request.getEmail());
        List<SupplierResponseDto> list = List.of(responseDto1, responseDto2);
        when(supplierPort.suppliers()).thenReturn(list);

        // 2. Act
        List<SupplierResponseDto> response = supplierUseCases.suppliers();

        // 3. Assert
        assertNotNull(response);
        verify(supplierPort, times(1)).suppliers();
    }
}