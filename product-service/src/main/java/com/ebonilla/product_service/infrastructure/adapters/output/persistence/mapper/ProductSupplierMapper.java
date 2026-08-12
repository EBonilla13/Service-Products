package com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper;

import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Product;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.ProductSupplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Supplier;

public class ProductSupplierMapper {
    public static ProductSupplier toEntity(com.ebonilla.product_service.domain.model.ProductSupplier domain, Product product, Supplier supplier){
        return new ProductSupplier(
                domain.getId(),
                domain.getPrice(),
                product,
                supplier
        );
    }

    public static com.ebonilla.product_service.domain.model.ProductSupplier toDomain(ProductSupplier entity){
        com.ebonilla.product_service.domain.model.ProductSupplier domain = new com.ebonilla.product_service.domain.model.ProductSupplier();
        domain.setId(entity.getId());
        domain.setPrice(entity.getPrice());
        domain.setProductId(entity.getProduct().getId());
        domain.setSupplierId(entity.getSupplier().getId());
        return domain;
    }

    public static ProductSupplier merge(com.ebonilla.product_service.domain.model.ProductSupplier domain, ProductSupplier entity,
                                        Product product, Supplier supplier){
        ProductSupplier entityMerge = new ProductSupplier();
        entityMerge.setId(entity.getId());
        entityMerge.setPrice(domain.getPrice());
        entityMerge.setProduct(product);
        entityMerge.setSupplier(supplier);
        return entityMerge;
    }

    public static ProductSupplierResponseDto toDto(ProductSupplier entity){
        return new ProductSupplierResponseDto(
                entity.getId(),
                entity.getPrice(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static ProductByFindSupplierDto productByFindSupplierDto(ProductSupplier productSupplier){
        return new ProductByFindSupplierDto(
                productSupplier.getProduct().getId(),
                productSupplier.getProduct().getName(),
                productSupplier.getProduct().getModel(),
                productSupplier.getProduct().getSpecification(),
                productSupplier.getProduct().getCategory().getCategoryName(),
                productSupplier.getProduct().getMeasurement().getSymbol(),
                productSupplier.getId(),
                productSupplier.getPrice(),
                productSupplier.getSupplier().getName(),
                productSupplier.getSupplier().getNumberPhone(),
                productSupplier.getSupplier().getEmail()
        );
    }
}
