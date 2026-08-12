package com.ebonilla.product_service.application.ports.output;

import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.domain.model.ProductSupplier;

import java.util.Optional;

public interface IProductSupplierPort {

    ProductSupplier create(ProductSupplier productSupplier);
    ProductSupplier update(ProductSupplier productSupplier);
    Optional<ProductSupplierResponseDto> findById(Integer id);
    Boolean exists(Integer id);
}
