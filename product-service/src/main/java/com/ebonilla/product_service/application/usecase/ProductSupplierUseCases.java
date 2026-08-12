package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.productsupplier.request.ProductSupplierRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.mapper.ProductSupplierMapper;
import com.ebonilla.product_service.application.ports.input.IProductSupplierRepository;
import com.ebonilla.product_service.application.ports.output.IPSRelationPort;
import com.ebonilla.product_service.application.ports.output.IProductPort;
import com.ebonilla.product_service.application.ports.output.IProductSupplierPort;
import com.ebonilla.product_service.application.ports.output.ISupplierPort;
import com.ebonilla.product_service.domain.model.ProductSupplier;
import com.ebonilla.product_service.domain.validation.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductSupplierUseCases implements IProductSupplierRepository {

    private final IProductSupplierPort productSupplierPort;
    private final IProductPort productPort;
    private final ISupplierPort supplierPort;
    private final IPSRelationPort relationPort;

    @Override
    public ProductSupplierResponseDto create(ProductSupplierRequestDto request) {
        ProductSupplier valid = this.validate(request);

        ProductSupplier entitySaved = productSupplierPort.create(valid);

        return ProductSupplierMapper.toDto(entitySaved);
    }

    @Override
    public ProductSupplierResponseDto update(ProductSupplierRequestDto request) {
        if (request.getId() == null)
            throw new IdNullException();

        ProductSupplier valid = this.validate(request);

        ProductSupplier entityUpdate = productSupplierPort.update(valid);

    return ProductSupplierMapper.toDto(entityUpdate);
    }

    @Override
    public ProductSupplierResponseDto findById(Integer id) {
        return productSupplierPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + id));
    }

    @Override
    public PSRelationResponseDto findByForeignKeys(Integer productId, Integer supplierId) {
        return relationPort.findByForeignKeys(productId, supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("product ID " + productId + " and supplier ID " + supplierId));
    }

    @Override
    public SPSuppliersByFindProductDto spSuppliersFindByProduct(Integer productId) {
        return relationPort.spSuppliersByProduct(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product ID " + productId));
    }

    @Override
    public List<ProductByFindSupplierDto> productFindBySupplier(Integer supplierId) {
        return List.copyOf(relationPort.productBySupplier(supplierId));
    }


    // Valida las campos para create or update
    private ProductSupplier validate(ProductSupplierRequestDto request){

        Notification notification = new Notification();

        ProductSupplier validModel = ProductSupplierMapper.toDomain(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());
        else if (!productPort.exists(request.getProductId()))
            throw new ResourceNotFoundException("product ID " + request.getProductId());
        else if (!supplierPort.exists(request.getSupplierId()))
            throw new ResourceNotFoundException("supplier ID " + request.getSupplierId());

        return validModel;
    }
}
