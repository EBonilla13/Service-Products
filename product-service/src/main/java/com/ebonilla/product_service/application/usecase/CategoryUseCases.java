package com.ebonilla.product_service.application.usecase;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.application.exception.BusinessLogicException;
import com.ebonilla.product_service.application.exception.IdNullException;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.mapper.CategoryMapper;
import com.ebonilla.product_service.application.ports.input.ICategoryRespository;
import com.ebonilla.product_service.application.ports.output.ICategoryPort;
import com.ebonilla.product_service.domain.model.Category;
import com.ebonilla.product_service.domain.validation.Notification;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryUseCases implements ICategoryRespository {

    private final ICategoryPort categoryPort;

    @Override
    public CategoryResponseDto create(CategoryRequestDto request) {
        // Se recibe el request DTO y se mapea a entidad
        Notification notification = new Notification();

        Category category = CategoryMapper.toEntity(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());

        // Se retorna la entidad mapeada a response DTO
        Category categorySaved = categoryPort.save(category);

        return CategoryMapper.toResponseDto(categorySaved);
    }

    @Override
    public CategoryResponseDto update(CategoryRequestDto request) {
        if (request.getId() == null)
            throw new IdNullException();

        Notification notification = new Notification();

        Category category = CategoryMapper.toEntity(request, notification);

        if (notification.hasErrors())
            throw new BusinessLogicException(notification.getErrors());

        Category categoryUpdated = categoryPort.save(category);

        return CategoryMapper.toResponseDto(categoryUpdated);
    }

    @Override
    public CategoryResponseDto findById(Integer categoryId) {
        return categoryPort.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + categoryId));
    }

    @Override
    public List<CategoryResponseDto> categories() {
        return categoryPort.categories()
                .stream()
                .toList();
    }
}
