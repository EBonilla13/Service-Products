package com.ebonilla.product_service.application.ports.input;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;

import java.util.List;

public interface IMeasurementRepository {

    MeasurementResponseDto create(MeasurementRequestDto request);
    MeasurementResponseDto update(MeasurementRequestDto request);
    MeasurementResponseDto findById(Integer id);
    List<MeasurementResponseDto> measurements();
    void delete(Integer id);
}
