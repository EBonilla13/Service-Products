package com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository;

import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategory extends JpaRepository <Category, Integer> {
    Optional<Category> findByCategoryName(String name);
}
