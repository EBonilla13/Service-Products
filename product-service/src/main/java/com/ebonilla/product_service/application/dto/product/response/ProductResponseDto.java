package com.ebonilla.product_service.application.dto.product.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class ProductResponseDto {

    private final Integer id;
    private final String name;
    private final String model;
    private final String specification;
    private final Integer categoryId;
    private final Integer measurementId;
    private final Instant createdAt;
    private final Instant updatedAt;

}
