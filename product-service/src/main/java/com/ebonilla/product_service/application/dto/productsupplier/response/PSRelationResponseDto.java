package com.ebonilla.product_service.application.dto.productsupplier.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class PSRelationResponseDto {

    private final Integer id;
    private final BigDecimal price;

    private final String productName;
    private final String model;
    private final String specification;

    private final String supplierName;
    private final String phone;
    private final String email;

}
