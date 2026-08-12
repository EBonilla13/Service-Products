package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.mapper.MeasurementMapper;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.input.IMeasurementRepository;
import com.ebonilla.product_service.application.ports.output.IMeasurementPort;
import com.ebonilla.product_service.domain.model.Measurement;
import com.ebonilla.product_service.domain.validation.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MeasurementUseCases implements IMeasurementRepository {

    private final IMeasurementPort iMeasurementPort;

    @Override
    public MeasurementResponseDto create(MeasurementRequestDto request) {
        Notification notification = new Notification();

        Measurement validMeasurement = MeasurementMapper.toDomain(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());

        Measurement measurementSaved = iMeasurementPort.create(validMeasurement);

        return MeasurementMapper.toResponseDto(measurementSaved);
    }

    @Override
    public MeasurementResponseDto update(MeasurementRequestDto request) {
        if (request.getId() == null)
            throw new IdNullException();

        Notification notification = new Notification();

        Measurement validMeasurement = MeasurementMapper.toDomain(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());

        Measurement measurementUpdated = iMeasurementPort.update(validMeasurement);

        return MeasurementMapper.toResponseDto(measurementUpdated);
    }

    @Override
    public MeasurementResponseDto findById(Integer id) {
        return iMeasurementPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id));
    }

    @Override
    public List<MeasurementResponseDto> measurements() {
        return iMeasurementPort.measurements()
                .stream()
                .toList();
    }

    @Override
    public void delete(Integer id) {
        if (!iMeasurementPort.exists(id))
            throw new ResourceNotFoundException("Id " + id);

        iMeasurementPort.delete(id);
    }
}
