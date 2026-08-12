package com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper;

import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Measurement;

public class MeasurementMapper {

    public static Measurement toEntity(com.ebonilla.product_service.domain.model.Measurement measurement){
        return new Measurement(
                measurement.getId(),
                measurement.getUnit(),
                measurement.getSymbol()
        );
    }

    public static com.ebonilla.product_service.domain.model.Measurement toDomain(Measurement measurementEntity){
        com.ebonilla.product_service.domain.model.Measurement measurement = new com.ebonilla.product_service.domain.model.Measurement();
        measurement.setId(measurementEntity.getId());
        measurement.setUnit(measurementEntity.getUnit());
        measurement.setSymbol(measurementEntity.getSymbol());
        return measurement;
    }

    public static Measurement merge(Measurement measurementEntity, com.ebonilla.product_service.domain.model.Measurement measurementDomain){
        return new Measurement(
                measurementEntity.getId(),
                measurementDomain.getUnit(),
                measurementDomain.getSymbol()
        );
    }

    public static MeasurementResponseDto toDto(Measurement measurementEntity){
        return new MeasurementResponseDto(
                measurementEntity.getId(),
                measurementEntity.getUnit(),
                measurementEntity.getSymbol(),
                measurementEntity.getCreatedAt(),
                measurementEntity.getUpdatedAt()
        );
    }
}
