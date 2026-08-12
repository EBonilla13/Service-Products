package com.ebonilla.product_service.application.ports.input;

import com.ebonilla.product_service.application.dto.productsupplier.request.PSRelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;

public interface IProductSupplierRelation {

    PSRelationResponseDto create(PSRelationRequestDto request);
}
