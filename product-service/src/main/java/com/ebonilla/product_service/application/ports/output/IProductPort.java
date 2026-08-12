package com.ebonilla.product_service.application.ports.output;

import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductPort {

    Product create(Product product);
    Product update(Product product);
    Optional<ProductResponseDto> findById(Integer id);
    Optional<ProductResponseDto> findByName(String name);
    Optional<ProductResponseDto> findByModel(String model);
    Optional<ProductResponseDto> findBySpecification(String spec);
    List<ProductResponseDto> products();
    List<ProductResponseDto> productsByCategoryId(Integer categoryId);
    Boolean exists(Integer id);
}
