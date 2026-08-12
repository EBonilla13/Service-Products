package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.mapper.SupplierMapper;
import com.ebonilla.product_service.application.ports.input.ISupplierRepository;
import com.ebonilla.product_service.application.ports.output.ISupplierPort;
import com.ebonilla.product_service.domain.model.Supplier;
import com.ebonilla.product_service.domain.validation.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SupplierUseCases implements ISupplierRepository {

    private final ISupplierPort supplierPort;

    @Override
    public SupplierResponseDto create(SupplierRequestDto request) {
        Supplier validSupplier = validation(request);

        Supplier supplierSaved = supplierPort.create(validSupplier);

        return SupplierMapper.toDto(supplierSaved);
    }

    @Override
    public SupplierResponseDto update(SupplierRequestDto request) {
        if (request.getId() == null)
            throw new IdNullException();

        Supplier validSupplier = validation(request);

        Supplier supplierUpdated = supplierPort.update(validSupplier);

        return SupplierMapper.toDto(supplierUpdated);
    }

    @Override
    public SupplierResponseDto findById(Integer supplierId) {
        return supplierPort.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + supplierId));
    }

    @Override
    public SupplierResponseDto findByName(String supplierName) {
        return supplierPort.findByName(supplierName)
                .orElseThrow(() -> new ResourceNotFoundException(supplierName));
    }

    @Override
    public List<SupplierResponseDto> suppliers() {
        return List.copyOf(supplierPort.suppliers());
    }


    private Supplier validation(SupplierRequestDto request){
        Notification notification = new Notification();

        Supplier validSupplier = SupplierMapper.toDomain(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());

        return validSupplier;
    }
}
