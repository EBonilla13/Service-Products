package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter;

import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.IMeasurementPort;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Measurement;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.MeasurementMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IMeasurement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MeasurementPersistenceAdapter implements IMeasurementPort {

    private final IMeasurement iMeasurement;

    @Override
    public com.ebonilla.product_service.domain.model.Measurement create(
            com.ebonilla.product_service.domain.model.Measurement measurementDomain) {

        Measurement measurementEntitiy = MeasurementMapper.toEntity(measurementDomain);

        Measurement measurementSaved = iMeasurement.save(measurementEntitiy);

        return MeasurementMapper.toDomain(measurementSaved);
    }

    @Override
    public com.ebonilla.product_service.domain.model.Measurement update(
            com.ebonilla.product_service.domain.model.Measurement measurementDomain) {
        Measurement measurement = iMeasurement.findById(measurementDomain.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ID " + measurementDomain.getId()));

        Measurement measurementMerge = MeasurementMapper.merge(measurement, measurementDomain);

        Measurement measurementUpdate = iMeasurement.save(measurementMerge);

        return MeasurementMapper.toDomain(measurementUpdate);
    }

    @Override
    public Optional<MeasurementResponseDto> findById(Integer id) {
        return iMeasurement.findById(id)
                .map(MeasurementMapper::toDto);
    }

    @Override
    public List<MeasurementResponseDto> measurements() {
        return iMeasurement.findAll()
                .stream()
                .map(MeasurementMapper::toDto)
                .toList();
    }

    @Override
    public Boolean exists(Integer id) {
        return iMeasurement.existsById(id);
    }

    @Override
    public void delete(Integer id) {
        iMeasurement.deleteById(id);
    }
}
