package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter;

import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.ISupplierPort;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Supplier;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.SupplierMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ISupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SupplierPersistenceAdapter implements ISupplierPort {

    private final ISupplier supplierJpa;

    @Override
    public com.ebonilla.product_service.domain.model.Supplier create(com.ebonilla.product_service.domain.model.Supplier supplier) {
        Supplier supplierEntity = SupplierMapper.toEntity(supplier);

        Supplier supplierSaved = supplierJpa.save(supplierEntity);

        return SupplierMapper.toDomain(supplierSaved);
    }

    @Override
    public com.ebonilla.product_service.domain.model.Supplier update(com.ebonilla.product_service.domain.model.Supplier supplier) {
        Supplier dbSupplier = supplierJpa.findById(supplier.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ID " + supplier.getId()));

        Supplier supplierMerge = SupplierMapper.merge(supplier, dbSupplier);

        Supplier supplierUpdated = supplierJpa.save(supplierMerge);

        return SupplierMapper.toDomain(supplierUpdated);
    }

    @Override
    public Optional<SupplierResponseDto> findById(Integer id) {
        return supplierJpa.findById(id)
                .map(SupplierMapper::toDto);
    }

    @Override
    public Optional<SupplierResponseDto> findByName(String name) {
        return supplierJpa.findByName(name)
                .map(SupplierMapper::toDto);
    }

    @Override
    public Boolean exists(Integer id) {
        return supplierJpa.existsById(id);
    }

    @Override
    public List<SupplierResponseDto> suppliers() {
        return supplierJpa.findAll()
                .stream()
                .map(SupplierMapper::toDto)
                .toList();
    }
}
