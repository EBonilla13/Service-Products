package com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper;

import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Category;

public class CategoryMapper {

    public static Category toInfrastructureEntity(com.ebonilla.product_service.domain.model.Category category){
        return new Category(
                category.getId(),
                category.getCategoryName()
        );
    }

    public static Category merge(Category categoryEntity, com.ebonilla.product_service.domain.model.Category categoryDomain){
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setCategoryName(categoryDomain.getCategoryName());
        category.setCreatedAt(categoryEntity.getCreatedAt());
        category.setUpdatedAt(categoryEntity.getUpdatedAt());
        return category;
    }

    public static com.ebonilla.product_service.domain.model.Category toDomainEntity(Category category){
        com.ebonilla.product_service.domain.model.Category categoryDomain = new com.ebonilla.product_service.domain.model.Category();
        categoryDomain.setId(category.getId());
        categoryDomain.setCategoryName(category.getCategoryName());
        return categoryDomain;
    }

    public static CategoryResponseDto toDto(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}