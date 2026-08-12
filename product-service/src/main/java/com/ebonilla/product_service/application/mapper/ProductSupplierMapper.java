package com.ebonilla.product_service.application.mapper;

import com.ebonilla.product_service.application.dto.productsupplier.request.ProductSupplierRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import com.ebonilla.product_service.domain.validation.Notification;

public class ProductSupplierMapper {

    public static ProductSupplier toDomain(ProductSupplierRequestDto request, Notification notification){
        return ProductSupplier.create(
                request.getId(),
                request.getPrice(),
                request.getProductId(),
                request.getSupplierId(),
                notification
        );
    }

    public static ProductSupplierResponseDto toDto(ProductSupplier productSupplier){
        return new ProductSupplierResponseDto(
                productSupplier.getId(),
                productSupplier.getPrice()
        );
    }
}
