package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter;

import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
import com.ebonilla.product_service.application.exception.ResourceNotFoundException;
import com.ebonilla.product_service.application.ports.output.ICategoryPort;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Category;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.CategoryMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.ICategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements ICategoryPort {

    private final ICategory iCategory;

    // Puntos basicos a realizar:
    // 1. Mapear entidad de dominio a entidad de infraestructura (Lo que recibe los metodos)
    // 2. Aplicar metodos de persistencia (guardar, actualizar, leer, eliminar)
    // 3. Mapear entidad de infraestructura a entidad de dominio (lo que devuelve)


    @Override
    public com.ebonilla.product_service.domain.model.Category save(com.ebonilla.product_service.domain.model.Category categoryDomain) {
        Category categoryEntity = CategoryMapper.toInfrastructureEntity(categoryDomain);

        Category categorySaved = iCategory.save(categoryEntity);

        return CategoryMapper.toDomainEntity(categorySaved);
    }

    @Override
    public com.ebonilla.product_service.domain.model.Category update(com.ebonilla.product_service.domain.model.Category category) {
        Category categoryEntity = iCategory.findById(category.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Id " + category.getId()));

        Category categoryUpdate = CategoryMapper.merge(categoryEntity, category);

        Category categorySaved = iCategory.save(categoryUpdate);

        return CategoryMapper.toDomainEntity(categorySaved);
    }


    @Override
    public Optional<CategoryResponseDto> findById(Integer id) {
        return iCategory.findById(id)
                .map(CategoryMapper::toDto);
    }

    @Override
    public Boolean existsCategoryById(Integer categoryId) {
        return iCategory.existsById(categoryId);
    }

    @Override
    public List<CategoryResponseDto> categories() {
        return iCategory.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }
}