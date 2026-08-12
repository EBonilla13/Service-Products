package com.ebonilla.product_service.application.ports.output;

import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.domain.model.Measurement;

import java.util.List;
import java.util.Optional;

public interface IMeasurementPort {

    Measurement create(Measurement measurement);
    Measurement update(Measurement measurement);
    Optional<MeasurementResponseDto> findById(Integer id);
    List<MeasurementResponseDto> measurements();
    Boolean exists(Integer id);
    void delete(Integer id);
}
