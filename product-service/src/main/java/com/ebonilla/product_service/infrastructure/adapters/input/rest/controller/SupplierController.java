package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.application.usecase.SupplierUseCases;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract.SupplierApi;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController implements SupplierApi {

    private final SupplierUseCases supplierUseCases;

    @PreAuthorize("hasAuthority('SCOPE_admin') or hasAnyAuthority('SCOPE_supplier:write', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<SupplierResponseDto>> create( SupplierRequestDto request ){

        SupplierResponseDto response = supplierUseCases.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.success(response)
        );
    }

    @PreAuthorize("hasAuthority('SCOPE_admin') or hasAnyAuthority('SCOPE_supplier:write', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<SupplierResponseDto>> update(SupplierRequestDto request){

        SupplierResponseDto response = supplierUseCases.update(request);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<SupplierResponseDto>> findById(Integer id){

        SupplierResponseDto response = supplierUseCases.findById(id);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<SupplierResponseDto>> findByName(String name){

        SupplierResponseDto response = supplierUseCases.findByName(name);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<List<SupplierResponseDto>>> suppliers(){

        List<SupplierResponseDto> response = supplierUseCases.suppliers();

        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(BaseResponse.success(response));
    }
}
