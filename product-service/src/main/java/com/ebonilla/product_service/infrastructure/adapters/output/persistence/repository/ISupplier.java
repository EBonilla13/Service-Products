package com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository;

import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISupplier extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> findByName(String name);
}
