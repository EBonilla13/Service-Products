package com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository;

import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMeasurement extends JpaRepository<Measurement, Integer> {

}
