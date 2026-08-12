package com.ebonilla.product_service.application.ports.input;

import com.ebonilla.product_service.application.dto.productsupplier.request.ProductSupplierRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;

import java.util.List;


public interface IProductSupplierRepository {

    ProductSupplierResponseDto create(ProductSupplierRequestDto request);
    ProductSupplierResponseDto update(ProductSupplierRequestDto request);
    ProductSupplierResponseDto findById(Integer id);
    PSRelationResponseDto findByForeignKeys(Integer productId, Integer supplierId);
    SPSuppliersByFindProductDto spSuppliersFindByProduct(Integer productId);
    List<ProductByFindSupplierDto> productFindBySupplier(Integer supplierId);

}
