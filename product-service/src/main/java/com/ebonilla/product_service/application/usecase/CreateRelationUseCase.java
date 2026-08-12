package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.productsupplier.request.PSRelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.mapper.PSRelationMapper;
import com.ebonilla.product_service.application.ports.input.IProductSupplierRelation;
import com.ebonilla.product_service.application.ports.output.*;
import com.ebonilla.product_service.application.ports.output.*;
import com.ebonilla.product_service.domain.model.Product;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import com.ebonilla.product_service.domain.model.Supplier;
import com.ebonilla.product_service.domain.validation.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreateRelationUseCase implements IProductSupplierRelation {

    private final ICategoryPort categoryPort;
    private final IMeasurementPort measurementPort;
    private final IProductPort productPort;
    private final ISupplierPort supplierPort;
    private final IProductSupplierPort relationPort;

    @Override
    @Transactional
    public PSRelationResponseDto create(PSRelationRequestDto request) {

        Notification notification = new Notification();

        Product validProduct = PSRelationMapper.toProductDomain(request.getProduct(), notification);
        Supplier validSupplier = PSRelationMapper.toSupplierDomain(request.getSupplier(), notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());
        else if (!categoryPort.existsCategoryById(request.getProduct().getCategoryId()))
            throw new ResourceNotFoundException("Category ID " + request.getProduct().getCategoryId());
        else if (!measurementPort.exists(request.getProduct().getMeasurementId()))
            throw new ResourceNotFoundException("Measurement ID " + request.getProduct().getMeasurementId());

        Product productSaved = productPort.create(validProduct);
        Supplier supplierSaved = supplierPort.create(validSupplier);

        ProductSupplier validRelation = PSRelationMapper.toPSDomain(
                request.getRelation(), productSaved.getId(), supplierSaved.getId(), notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());

        ProductSupplier relationSaved = relationPort.create(validRelation);

        return PSRelationMapper.toDto(productSaved, supplierSaved, relationSaved);
    }

}
