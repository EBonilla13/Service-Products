package com.ebonilla.product_service.application.ports.input;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;

import java.util.List;

public interface ISupplierRepository {

    SupplierResponseDto create(SupplierRequestDto request);
    SupplierResponseDto update(SupplierRequestDto request);
    SupplierResponseDto findById(Integer supplierId);
    SupplierResponseDto findByName(String supplierName);
    List<SupplierResponseDto> suppliers();
}
