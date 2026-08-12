package com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper;

import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Supplier;

public class SupplierMapper {

    public static Supplier toEntity(com.ebonilla.product_service.domain.model.Supplier supplierDomain){
        return new Supplier(
                supplierDomain.getId(),
                supplierDomain.getSupplierName(),
                supplierDomain.getNumberPhone(),
                supplierDomain.getEmail()
        );
    }

    public static com.ebonilla.product_service.domain.model.Supplier toDomain(Supplier entity){
        com.ebonilla.product_service.domain.model.Supplier domain = new com.ebonilla.product_service.domain.model.Supplier();
        domain.setId(entity.getId());
        domain.setSupplierName(entity.getName());
        domain.setNumberPhone(entity.getNumberPhone());
        domain.setEmail(entity.getEmail());
        return domain;
    }

    public static Supplier merge(com.ebonilla.product_service.domain.model.Supplier domain, Supplier entity){
        Supplier supplier = new Supplier();
        supplier.setId(entity.getId());
        supplier.setName(domain.getSupplierName());
        supplier.setNumberPhone(domain.getNumberPhone());
        supplier.setEmail(domain.getEmail());
        return supplier;
    }

    public static SupplierResponseDto toDto( Supplier entity){
        return new SupplierResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getNumberPhone(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
