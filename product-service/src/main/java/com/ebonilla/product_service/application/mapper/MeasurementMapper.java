package com.ebonilla.product_service.application.mapper;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.domain.model.Measurement;
import com.ebonilla.product_service.domain.validation.Notification;

public class MeasurementMapper {

    public static Measurement toDomain(MeasurementRequestDto request, Notification notification){
        return Measurement.create(
                request.getId(),
                request.getUnit(),
                request.getSymbol(),
                notification);
    }

    public static MeasurementResponseDto toResponseDto(Measurement measurement){
        return new MeasurementResponseDto(
                measurement.getId(),
                measurement.getUnit(),
                measurement.getSymbol(),
                null,
                null
        );
    }
}
