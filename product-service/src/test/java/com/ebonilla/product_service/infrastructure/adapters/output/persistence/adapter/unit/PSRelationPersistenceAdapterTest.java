package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.unit;

import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSupplierResponseDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter.PSRelationPersistenceAdapter;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.ProductSupplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.ProductSupplierMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProductSupplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PSRelationPersistenceAdapterTest {

    @Mock private EntityManager em;
    @Mock private IProductSupplier productSupplier;
    @Mock private JsonMapper mapper;
    @Mock private Query query;

    @InjectMocks private PSRelationPersistenceAdapter adapter;

    @Test
    @DisplayName("Get DTO by product id and supplier id")
    void shouldReturnEntityWhenFindByForeignKeys(){
        // 1. Arrange
        Integer productId = 10, supplierId = 7;
        PSRelationResponseDto responseMock = mock(PSRelationResponseDto.class);
        when(productSupplier.findByForeignKeys(anyInt(), anyInt())).thenReturn(Optional.of(responseMock));

        // 2. Act
        Optional<PSRelationResponseDto> response = adapter.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isPresent());
        verify(productSupplier, times(1)).findByForeignKeys(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Get optional empty by product id and supplier id")
    void shouldReturnOptionalEmptyWhenFindByForeignKeys(){
        // 1. Arrange
        Integer productId = 10, supplierId = 7;
        when(productSupplier.findByForeignKeys(anyInt(), anyInt())).thenReturn(Optional.empty());

        // 2. Act
        Optional<PSRelationResponseDto> response = adapter.findByForeignKeys(productId, supplierId);

        // 3. Assert
        assertTrue(response.isEmpty());
        verify(productSupplier, times(1)).findByForeignKeys(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Get suppliers by product id")
    void shouldFindSuppliersByProductId(){
        // 1. Arrange
        Integer productId = 12;
        String jsonResponse = createJson(true);
        SPSuppliersByFindProductDto expectDto = createDto(true);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter("p_product_id", productId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(jsonResponse);

        when(mapper.readValue(jsonResponse, SPSuppliersByFindProductDto.class)).thenReturn(expectDto);

        // 2. Act
        Optional<SPSuppliersByFindProductDto> response = adapter.spSuppliersByProduct(productId);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getSuppliers().size()).isEqualTo(2);

        verify(em, times(1)).createNativeQuery("SELECT fun_get_suppliers_of_product(:p_product_id)::text");
        verify(query, times(1)).setParameter("p_product_id", productId);
        verify(mapper, times(1)).readValue(jsonResponse, SPSuppliersByFindProductDto.class);
    }

    @Test
    @DisplayName("Get product without suppliers")
    void shouldFindSuppliersEmptyByProductId() {
        // 1. Arrange
        Integer productId = 12;
        String jsonResponse = createJson(false);
        SPSuppliersByFindProductDto expectDto = createDto(false);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter("p_product_id", productId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(jsonResponse);

        when(mapper.readValue(jsonResponse, SPSuppliersByFindProductDto.class)).thenReturn(expectDto);

        // 2. Act
        Optional<SPSuppliersByFindProductDto> response = adapter.spSuppliersByProduct(productId);

        // 3. Assert
        assertTrue(response.isPresent());
        assertThat(response.get().getSuppliers().size()).isEqualTo(0);

        verify(em, times(1)).createNativeQuery("SELECT fun_get_suppliers_of_product(:p_product_id)::text");
        verify(query, times(1)).setParameter("p_product_id", productId);
        verify(mapper, times(1)).readValue(jsonResponse, SPSuppliersByFindProductDto.class);
    }

    @Test
    @DisplayName("Get Optional empty by product not found")
    void shouldReturnOptionalEmptyFindByProductId() {
        // 1. Arrange
        Integer productId = 12;
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter("p_product_id", productId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(null);

        // 2. Act
        Optional<SPSuppliersByFindProductDto> response = adapter.spSuppliersByProduct(productId);

        // 3. Assert
        assertTrue(response.isEmpty());

        verify(em, times(1)).createNativeQuery("SELECT fun_get_suppliers_of_product(:p_product_id)::text");
        verify(query, times(1)).setParameter("p_product_id", productId);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Throw JacksonException")
    void shouldThrowJacksonExceptionFindSuppliersByProductId() {
        // 1. Arrange
        Integer productId = 12;
        String json = "{json:invalid}";
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter("p_product_id", productId)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(json);

        when(mapper.readValue(json, SPSuppliersByFindProductDto.class))
                .thenThrow(new JacksonException("simulated parsing error") {});

        // 2. Act
        assertThatThrownBy(() -> adapter.spSuppliersByProduct(productId))
                .isInstanceOf(RuntimeException.class);

        // 3. Assert
        verify(em, times(1)).createNativeQuery("SELECT fun_get_suppliers_of_product(:p_product_id)::text");
        verify(query, times(1)).setParameter("p_product_id", productId);
        verify(mapper).readValue(json, SPSuppliersByFindProductDto.class);
    }

    @Test
    @DisplayName("Return List of DTOs")
    void shouldReturnListOfDTOs(){
        // 1. Arrange
        Integer supplierId = 5;

        ProductSupplier mock1 = mock(ProductSupplier.class);
        ProductSupplier mock2 = mock(ProductSupplier.class);
        List<ProductSupplier> list = List.of(mock1, mock2);

        ProductByFindSupplierDto producto1 = new ProductByFindSupplierDto(
                101, "Laptop ThinkPad X1 Carbon", "Gen 11", "32GB RAM, 1TB SSD, Intel i7", "Cómputo",
                "pza", 5001, new BigDecimal("1450.00"), "Lenovo Comercial México", "5512345678", "ventas@lenovo.com"
        );
        ProductByFindSupplierDto producto2 = new ProductByFindSupplierDto(
                102, "Monitor UltraWide 34\"", "34WN750-B", "IPS, QHD, 75Hz, HDR10", "Monitores y Pantallas",
                "pza", 5002, new BigDecimal("420.50"), "Distribuidora LG LATAM", "5587654321", "soporte@lgdistribucion.com"
        );

        // Definimos el comportamiento del respositorio
        when(productSupplier.findBySupplierId(anyInt())).thenReturn(list);

        // Al tener metodos estaticos en el Mapper se debe usar Mockito.mockStatic
        try (MockedStatic<ProductSupplierMapper> mockMapper = mockStatic(ProductSupplierMapper.class)) {

            // Se define el comportamiento del mapper para cada entidad
            mockMapper.when(() -> ProductSupplierMapper.productByFindSupplierDto(mock1)).thenReturn(producto1);
            mockMapper.when(() -> ProductSupplierMapper.productByFindSupplierDto(mock2)).thenReturn(producto2);

            // 2. Act
            List<ProductByFindSupplierDto> response = adapter.productBySupplier(supplierId);

            // 3. Assert
            assertFalse(response.isEmpty());
            assertThat(response.size()).isEqualTo(2);
            verify(productSupplier, times(1)).findBySupplierId(anyInt());
        }
    }

    @Test
    @DisplayName("Return List of DTOs empty")
    void shouldReturnListOfDTOsEmpty(){
        // 1. Arrange
        Integer supplierId = 10;
        List<ProductSupplier> list = new ArrayList<>();
        when(productSupplier.findBySupplierId(anyInt())).thenReturn(list);

        // 2. Act
        List<ProductByFindSupplierDto> response = adapter.productBySupplier(supplierId);

        // 3. Assert
        assertTrue(response.isEmpty());
        verify(productSupplier, times(1)).findBySupplierId(anyInt());
    }

    private String createJson(Boolean addSuppliers){
        String json = "{\"product_id\":\"12\",\"product_name\":\"laptop\", \"product_model\":\"lenovo thinkpad\", \"product_specification\":\"t410\","+
                "\"category\":\"technology\", \"symbol\":\"pza\", \"suppliers\":";
        String suppliers = "[]}";
        if (addSuppliers){
            suppliers = "[{\"supplier_name\":\"elizondo\", \"supplier_phone\":\"8123123456\"," +
        "\"supplier_email\":\"elizondo_email@gmail.com\", \"price\":\"5999.89\"},{\"supplier_name\":\"elektra\", \"supplier_phone\":\"8123453456\"," +
                    "\"supplier_email\":\"elektra_email@gmail.com\", \"price\":\"5499.89\"}]}";
        }
        return json + suppliers;
    }

    private SPSuppliersByFindProductDto createDto(Boolean addSuppliers){
        SPSuppliersByFindProductDto dto = new SPSuppliersByFindProductDto();
        dto.setId(12);
        dto.setProductName("laptop");
        dto.setModel("lenovo thinkpad");
        dto.setSpecification("t410");
        dto.setCategory("technology");
        dto.setSymbol("pza");
        dto.setSuppliers(new ArrayList<>());

        if (addSuppliers){
            SPSupplierResponseDto supplier1 = new SPSupplierResponseDto();
            supplier1.setSupplierName("elizondo");
            supplier1.setPhone("8123123456");
            supplier1.setEmail("elizondo_email@gmail.com");
            supplier1.setPrice(new BigDecimal("5999.89"));

            SPSupplierResponseDto supplier2 = new SPSupplierResponseDto();
            supplier2.setSupplierName("elektra");
            supplier2.setPhone("8123453456");
            supplier2.setEmail("elektra_email@gmail.com");
            supplier2.setPrice(new BigDecimal("5499.89"));
            dto.setSuppliers(List.of(supplier1, supplier2));
        }
        return dto;
    }
}