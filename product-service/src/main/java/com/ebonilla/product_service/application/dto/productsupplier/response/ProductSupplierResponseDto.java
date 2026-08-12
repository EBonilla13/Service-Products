package com.ebonilla.product_service.application.dto.productsupplier.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@EqualsAndHashCode
@ToString
public class ProductSupplierResponseDto {

    private final Integer id;
    private final BigDecimal price;
    private Instant createdAt;
    private Instant updatedAt;

    public ProductSupplierResponseDto(Integer id, BigDecimal price){
        this.id = id;
        this.price = price;
    }

    public ProductSupplierResponseDto(Integer id, BigDecimal price, Instant createdAt, Instant updatedAt){
        this(id, price);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
