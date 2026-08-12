package com.ebonilla.product_service.application.mapper;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.domain.model.Category;
import com.ebonilla.product_service.domain.validation.Notification;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDto request, Notification notification){
        return Category.create(request.getId(), request.getName(), notification);
    }

    public static CategoryResponseDto toResponseDto(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getCategoryName(),
                null,
                null
        );
    }
}
