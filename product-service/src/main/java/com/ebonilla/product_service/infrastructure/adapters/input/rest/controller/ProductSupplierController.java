package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller;

import com.ebonilla.product_service.application.dto.productsupplier.request.PSRelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.request.ProductSupplierRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.application.usecase.CreateRelationUseCase;
import com.ebonilla.product_service.application.usecase.ProductSupplierUseCases;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract.ProductSupplierApi;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-supplier")
@RequiredArgsConstructor
@Validated
public class ProductSupplierController implements ProductSupplierApi {

    private final ProductSupplierUseCases productSupplierUseCases;
    private final CreateRelationUseCase createRelationUseCase;

    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<ProductSupplierResponseDto>> create(ProductSupplierRequestDto request ){

        ProductSupplierResponseDto response = productSupplierUseCases.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.success(response)
        );
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<PSRelationResponseDto>> createRelation(PSRelationRequestDto request){
        PSRelationResponseDto response = createRelationUseCase.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.success(response)
        );
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<ProductSupplierResponseDto>> update(ProductSupplierRequestDto request ){

        ProductSupplierResponseDto response = productSupplierUseCases.update(request);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<ProductSupplierResponseDto>> findById(Integer id) {

        ProductSupplierResponseDto response = productSupplierUseCases.findById(id);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<PSRelationResponseDto>> findByForeignKeys(Integer productId, Integer supplierId){

        PSRelationResponseDto response = productSupplierUseCases.findByForeignKeys(productId, supplierId);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<SPSuppliersByFindProductDto>> suppliersByProductId(Integer productId){

        SPSuppliersByFindProductDto response = productSupplierUseCases.spSuppliersFindByProduct(productId);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<List<ProductByFindSupplierDto>>> productsBySupplierId(Integer supplierId){

        List<ProductByFindSupplierDto> response = productSupplierUseCases.productFindBySupplier(supplierId);

        return response.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(BaseResponse.success(response));
    }
}
