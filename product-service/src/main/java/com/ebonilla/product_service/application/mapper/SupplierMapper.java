package com.ebonilla.product_service.application.mapper;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.domain.model.Supplier;
import com.ebonilla.product_service.domain.validation.Notification;

public class SupplierMapper {

    public static Supplier toDomain(SupplierRequestDto request, Notification notification){
        return Supplier.create(
                request.getId(),
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                notification
        );
    }

    public static SupplierResponseDto toDto(Supplier supplier){
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getSupplierName(),
                supplier.getNumberPhone(),
                supplier.getEmail()
        );
    }
}
