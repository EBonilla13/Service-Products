package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.application.usecase.ProductUseCases;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract.ProductApi;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductUseCases productUseCases;

    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<ProductResponseDto>> create(ProductRequestDto request){

        ProductResponseDto response = productUseCases.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.success(response)
        );
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_admin', 'SCOPE_user')")
    @Override
    public ResponseEntity<BaseResponse<ProductResponseDto>> update(ProductRequestDto request){

        ProductResponseDto response = productUseCases.update(request);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDto>> findById(Integer id){
        ProductResponseDto response = productUseCases.findById(id);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDto>> findByName(String name){
        ProductResponseDto response = productUseCases.findByName(name);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDto>> findByModel(String model){
        ProductResponseDto response = productUseCases.findByModel(model);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<BaseResponse<ProductResponseDto>> findBySpecification(String specification){
        ProductResponseDto response = productUseCases.findBySpecification(specification);

        return ResponseEntity.ok(
                BaseResponse.success(response)
        );
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> products(){
        List<ProductResponseDto> productsList = productUseCases.products();

        return productsList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(productsList);
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> allProductsByCategoryId(Integer categoryId){
        List<ProductResponseDto> response = productUseCases.productsByCategoryId(categoryId);

        return response.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(response);
    }
}
