package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter;

import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.IProductSupplierPort;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.ProductSupplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Product;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Supplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.ProductSupplierMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProduct;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProductSupplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ISupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductSupplierPersistenceAdapter implements IProductSupplierPort {

    private final IProductSupplier psJpa;
    private final IProduct productJpa;
    private final ISupplier supplierJpa;

    @Override
    public com.ebonilla.product_service.domain.model.ProductSupplier create(com.ebonilla.product_service.domain.model.ProductSupplier productSupplier) {

        Product product = productJpa.findById(productSupplier.getProductId()).get();
        Supplier supplier = supplierJpa.findById(productSupplier.getSupplierId()).get();

        ProductSupplier entity = ProductSupplierMapper.toEntity(productSupplier, product, supplier);

        ProductSupplier entitySaved = psJpa.save(entity);

        return ProductSupplierMapper.toDomain(entitySaved);
    }

    @Override
    public com.ebonilla.product_service.domain.model.ProductSupplier update(com.ebonilla.product_service.domain.model.ProductSupplier productSupplier) {
        ProductSupplier entity = psJpa.findById(productSupplier.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ID " + productSupplier.getId()));

        Product product = productJpa.findById(productSupplier.getProductId()).get();
        Supplier supplier = supplierJpa.findById(productSupplier.getSupplierId()).get();

        ProductSupplier entityMerge = ProductSupplierMapper.merge(productSupplier, entity, product, supplier);

        ProductSupplier entitySaved = psJpa.save(entityMerge);

        return ProductSupplierMapper.toDomain(entitySaved);
    }

    @Override
    public Optional<ProductSupplierResponseDto> findById(Integer id) {
        return psJpa.findById(id)
                .map(ProductSupplierMapper::toDto);
    }

    @Override
    public Boolean exists(Integer id) {
        return psJpa.existsById(id);
    }
}
