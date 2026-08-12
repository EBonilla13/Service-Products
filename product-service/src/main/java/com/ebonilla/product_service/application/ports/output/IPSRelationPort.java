package com.ebonilla.product_service.application.ports.output;

import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;

import java.util.List;
import java.util.Optional;

public interface IPSRelationPort {

    Optional<PSRelationResponseDto> findByForeignKeys(Integer productId, Integer supplierId);
    Optional<SPSuppliersByFindProductDto> spSuppliersByProduct(Integer productId);
    List<ProductByFindSupplierDto> productBySupplier(Integer supplierId);
}
