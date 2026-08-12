package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.application.usecase.CategoryUseCases;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract.CategoryApi;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryUseCases categoryUseCases;

    @PreAuthorize("hasAuthority('SCOPE_admin') or hasAnyAuthority('SCOPE_category:write', 'SCOPE_user')")
    @Override
    public ResponseEntity<CategoryResponseDto> createCategory(CategoryRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryUseCases.create(request));
    }

    @PreAuthorize("hasAuthority('SCOPE_admin') or hasAnyAuthority('SCOPE_category:write', 'SCOPE_user')")
    @Override
    public ResponseEntity<CategoryResponseDto> updateCategory(CategoryRequestDto request){
        return ResponseEntity.ok(categoryUseCases.update(request));
    }

    @Override
    public ResponseEntity<CategoryResponseDto> findById(Integer idCategory){
        return ResponseEntity.ok(categoryUseCases.findById(idCategory));
    }

    @Override
    public ResponseEntity<List<CategoryResponseDto>> findAllCategories(){

        List<CategoryResponseDto> categories = categoryUseCases.categories();

        return categories.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(categories);
    }
}
