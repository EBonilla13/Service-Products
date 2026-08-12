package com.ebonilla.product_service.application.ports.output;

import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
import com.ebonilla.product_service.domain.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierPort {

    Supplier create(Supplier supplier);
    Supplier update(Supplier supplier);
    Optional<SupplierResponseDto> findById(Integer id);
    Optional<SupplierResponseDto> findByName(String name);
    Boolean exists(Integer id);
    List<SupplierResponseDto> suppliers();
}
