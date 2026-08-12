package com.ebonilla.product_service.application.ports.input;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryRespository {

    CategoryResponseDto create(CategoryRequestDto request);
    CategoryResponseDto update(CategoryRequestDto request);
    CategoryResponseDto findById(Integer categoryId);
    List<CategoryResponseDto> categories();

}
