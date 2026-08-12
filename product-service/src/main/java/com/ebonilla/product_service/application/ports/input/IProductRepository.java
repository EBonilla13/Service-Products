package com.ebonilla.product_service.application.ports.input;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;

import java.util.List;

public interface IProductRepository {

    ProductResponseDto create(ProductRequestDto request);
    ProductResponseDto update(ProductRequestDto request);
    ProductResponseDto findById(Integer productId);
    ProductResponseDto findByName(String productName);
    ProductResponseDto findByModel(String productModel);
    ProductResponseDto findBySpecification(String specification);
    List<ProductResponseDto> products();
    List<ProductResponseDto> productsByCategoryId(Integer categoryId);
}
