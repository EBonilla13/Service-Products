package com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper;

import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Category;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Measurement;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Product;

public class ProductMapper {

    public static Product toEntity(com.ebonilla.product_service.domain.model.Product productDomain, Category category, Measurement measurement){
        return new Product(
                productDomain.getId(),
                productDomain.getProductName(),
                productDomain.getProductModel(),
                productDomain.getProductSpecification(),
                category,
                measurement
        );
    }

    public static com.ebonilla.product_service.domain.model.Product toDomain(Product productEntity){
        com.ebonilla.product_service.domain.model.Product product = new com.ebonilla.product_service.domain.model.Product();
        product.setId(productEntity.getId());
        product.setProductName(productEntity.getName());
        product.setProductModel(productEntity.getModel());
        product.setProductSpecification(productEntity.getSpecification());
        product.setCategoryId(productEntity.getCategory().getId());
        product.setMeasurementId(productEntity.getMeasurement().getId());
        return product;
    }

    public static Product merge(com.ebonilla.product_service.domain.model.Product productDomain, Product productEntity, Category category, Measurement measurement){
        Product productMerge = new Product();
        productMerge.setId(productEntity.getId());
        productMerge.setName(productDomain.getProductName());
        productMerge.setModel(productDomain.getProductModel());
        productMerge.setSpecification(productDomain.getProductSpecification());
        productMerge.setCategory(category);
        productMerge.setMeasurement(measurement);

        return productMerge;
    }

    public static ProductResponseDto toDto(Product productEntity){
        return new ProductResponseDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getModel(),
                productEntity.getSpecification(),
                productEntity.getCategory().getId(),
                productEntity.getMeasurement().getId(),
                productEntity.getCreatedAt(),
                productEntity.getUpdatedAt()
        );
    }
}
