package com.ebonilla.product_service.application.mapper;

import com.ebonilla.product_service.application.dto.productsupplier.request.RelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.domain.model.Product;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import com.ebonilla.product_service.domain.model.Supplier;
import com.ebonilla.product_service.domain.validation.Notification;


public class PSRelationMapper {

    public static Product toProductDomain(
            com.ebonilla.product_service.application.dto.productsupplier.request.Product request, Notification notification){
        return Product.create(
                request.getId(),
                request.getName(),
                request.getModel(),
                request.getSpec(),
                request.getCategoryId(),
                request.getMeasurementId(),
                notification
        );
    }

    public static Supplier toSupplierDomain(
            com.ebonilla.product_service.application.dto.productsupplier.request.Supplier supplier, Notification notification){
        return Supplier.create(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                notification
        );
    }

    public static ProductSupplier toPSDomain(RelationRequestDto request, Integer productId, Integer supplierId, Notification notification){
        return ProductSupplier.create(
                null,
                request.getPrice(),
                productId,
                supplierId,
                notification
        );
    }

    public static PSRelationResponseDto toDto(Product product, Supplier supplier, ProductSupplier relation){
        return new PSRelationResponseDto(
                relation.getId(),
                relation.getPrice(),
                product.getProductName(),
                product.getProductModel(),
                product.getProductSpecification(),
                supplier.getSupplierName(),
                supplier.getNumberPhone(),
                supplier.getEmail()
        );
    }
}
