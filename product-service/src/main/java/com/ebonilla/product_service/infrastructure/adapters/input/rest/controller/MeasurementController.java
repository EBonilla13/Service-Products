package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.application.usecase.MeasurementUseCases;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract.MeasurementApi;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/measurement")
@RequiredArgsConstructor
public class MeasurementController implements MeasurementApi {

    private final MeasurementUseCases measurementUseCases;

    @PreAuthorize("hasAuthority('SCOPE_admin') or hasAnyAuthority('SCOPE_measurement:write', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<MeasurementResponseDto>> create(MeasurementRequestDto request){
        MeasurementResponseDto response = measurementUseCases.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                        BaseResponse.success(response));
    }

    @PreAuthorize("hasAuthority('SCOPE_admin') or hasAnyAuthority('SCOPE_measurement:write', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<MeasurementResponseDto>> update(MeasurementRequestDto request){

        MeasurementResponseDto response = measurementUseCases.update(request);

        return  ResponseEntity.ok(BaseResponse.success(response));
    }

    @Override
    public ResponseEntity<BaseResponse<MeasurementResponseDto>> findById(Integer id){

        return ResponseEntity.ok(BaseResponse.success(measurementUseCases.findById(id)));
    }

    @Override
    public ResponseEntity<BaseResponse<List<MeasurementResponseDto>>> measurements(){
        List<MeasurementResponseDto> list = measurementUseCases.measurements();
        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(BaseResponse.success(list));
    }

    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Override
    public ResponseEntity<Void> delete(Integer id){
        measurementUseCases.delete(id);
        return ResponseEntity.noContent().build();
    }
}
