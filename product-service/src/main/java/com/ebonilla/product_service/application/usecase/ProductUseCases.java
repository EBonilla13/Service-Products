package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.mapper.ProductMapper;
import com.ebonilla.product_service.application.ports.input.IProductRepository;
import com.ebonilla.product_service.application.ports.output.ICategoryPort;
import com.ebonilla.product_service.application.ports.output.IMeasurementPort;
import com.ebonilla.product_service.application.ports.output.IProductPort;
import com.ebonilla.product_service.domain.model.Product;
import com.ebonilla.product_service.domain.validation.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductUseCases implements IProductRepository {

    private final IProductPort iProductPort;
    private final ICategoryPort iCategoryPort;
    private final IMeasurementPort iMeasurementPort;

    @Override
    public ProductResponseDto create(ProductRequestDto request) {

        Product validProduct = validate(request);

        Product productSaved = iProductPort.create(validProduct);

        return ProductMapper.toResponseDto(productSaved);
    }

    @Override
    public ProductResponseDto update(ProductRequestDto request) {

        if (request.getId() == null)
            throw new IdNullException();

        Product validProduct = validate(request);

        Product productUpdated = iProductPort.update(validProduct);

        return ProductMapper.toResponseDto(productUpdated);
    }

    @Override
    public ProductResponseDto findById(Integer productId) {
        return iProductPort.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + productId));
    }

    @Override
    public ProductResponseDto findByName(String productName) {
        return iProductPort.findByName(productName)
                .orElseThrow(() -> new ResourceNotFoundException(productName));
    }

    @Override
    public ProductResponseDto findByModel(String productModel) {
        return iProductPort.findByModel(productModel)
                .orElseThrow(() -> new ResourceNotFoundException(productModel));
    }

    @Override
    public ProductResponseDto findBySpecification(String specification) {
        return iProductPort.findBySpecification(specification)
                .orElseThrow(() -> new ResourceNotFoundException(specification));
    }

    @Override
    public List<ProductResponseDto> products() {
        return List.copyOf(iProductPort.products());
    }

    @Override
    public List<ProductResponseDto> productsByCategoryId(Integer categoryId) {
        return List.copyOf(iProductPort.productsByCategoryId(categoryId));
    }

    private Product validate(ProductRequestDto request){
        Notification notification = new Notification();

        Product validProduct = ProductMapper.toDomain(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());
        else if (!iCategoryPort.existsCategoryById(request.getCategoryId()))
            throw new ResourceNotFoundException("category ID " + request.getCategoryId());
        else if (!iMeasurementPort.exists(request.getMeasurementId()))
            throw new ResourceNotFoundException("measurement ID " + request.getMeasurementId());

        return validProduct;
    }
}
