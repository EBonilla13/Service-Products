package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter;

import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.IProductPort;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Category;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Measurement;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Product;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.ProductMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ICategory;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IMeasurement;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements IProductPort {

    private final IProduct productJpa;
    private final ICategory categoryJpa;
    private final IMeasurement measurementJpa;

    @Override
    public com.ebonilla.product_service.domain.model.Product create(com.ebonilla.product_service.domain.model.Product product) {

        Category category = categoryJpa.findById(product.getCategoryId()).get();
        Measurement measurement = measurementJpa.findById(product.getMeasurementId()).get();

        Product productEntity = ProductMapper.toEntity(product, category, measurement);

        Product productSaved = productJpa.save(productEntity);

        return ProductMapper.toDomain(productSaved);
    }

    @Override
    public com.ebonilla.product_service.domain.model.Product update(com.ebonilla.product_service.domain.model.Product product) {

        Product productEntity = productJpa.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ID " + product.getId()));
        Category category = categoryJpa.findById(product.getCategoryId()).get();
        Measurement measurement = measurementJpa.findById(product.getMeasurementId()).get();

        Product productMerge = ProductMapper.merge(product, productEntity, category, measurement);

        Product productUpdated = productJpa.save(productMerge);

        return ProductMapper.toDomain(productUpdated);
    }

    @Override
    public Optional<ProductResponseDto> findById(Integer id) {
        return productJpa.findById(id)
                .map(ProductMapper::toDto);
    }

    @Override
    public Optional<ProductResponseDto> findByName(String name) {
        return productJpa.findByName(name)
                .map(ProductMapper::toDto);
    }

    @Override
    public Optional<ProductResponseDto> findByModel(String model) {
        return productJpa.findByModel(model)
                .map(ProductMapper::toDto);
    }

    @Override
    public Optional<ProductResponseDto> findBySpecification(String spec) {
        return productJpa.findBySpecification(spec)
                .map(ProductMapper::toDto);
    }

    @Override
    public List<ProductResponseDto> products() {
        return productJpa.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductResponseDto> productsByCategoryId(Integer categoryId) {
        return productJpa.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    @Override
    public Boolean exists(Integer id) {
        return productJpa.existsById(id);
    }
}
