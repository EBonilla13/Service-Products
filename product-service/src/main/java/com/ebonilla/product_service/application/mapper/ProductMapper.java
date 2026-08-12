package com.ebonilla.product_service.application.mapper;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.domain.model.Product;
import com.ebonilla.product_service.domain.validation.Notification;

public class ProductMapper {

    public static Product toDomain(ProductRequestDto request, Notification notification){
        return Product.create(
                request.getId(),
                request.getName(),
                request.getModel(),
                request.getSpecification(),
                request.getCategoryId(),
                request.getMeasurementId(),
                notification
        );
    }

    public static ProductResponseDto toResponseDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getProductName(),
                product.getProductModel(),
                product.getProductSpecification(),
                product.getCategoryId(),
                product.getMeasurementId(),
                null,
                null
        );
    }
}
