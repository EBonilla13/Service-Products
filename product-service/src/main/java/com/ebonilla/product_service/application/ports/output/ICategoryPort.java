package com.ebonilla.product_service.application.ports.output;

import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryPort {

    Category save(Category category);
    Category update(Category category);
    Optional<CategoryResponseDto> findById(Integer id);
    Boolean existsCategoryById(Integer categoryId);
    List<CategoryResponseDto> categories();

}
