package com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository;

import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IProduct extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    Optional<Product> findByModel(String model);
    Optional<Product> findBySpecification(String specification);
    List<Product> findByCategoryId(Integer id);

}
