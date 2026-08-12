package com.ebonilla.product_service.application.dto.measurement.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class MeasurementResponseDto {

    private final Integer id;
    private final String unit;
    private final String symbol;
    private final Instant createdAt;
    private final Instant updatedAt;

}
