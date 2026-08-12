package com.ebonilla.product_service.application.dto.category.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@ToString
public final class CategoryResponseDto {

    private final Integer id;
    private final String name;
    private final Instant createdAt;
    private final Instant updatedAt;

}
