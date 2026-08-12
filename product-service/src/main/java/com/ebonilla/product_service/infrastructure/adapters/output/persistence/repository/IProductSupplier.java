package com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository;

import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProductSupplier extends JpaRepository<ProductSupplier, Integer> {

    // En esta consulta multitabla se usa proyecciones que nos permite traer campos necesarios de la base de datos,
    // se requiere mapear a una Respuesta DTO desde la consulta.
    @Query("""
            SELECT new com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto(
                ps.id,
                ps.price,
                p.name,
                p.model,
                p.specification,
                s.name,
                s.numberPhone,
                s.email
            )
            FROM ProductSupplier ps
            JOIN ps.product p
            JOIN ps.supplier s
            WHERE p.id = :productId AND s.id = :supplierId
            """)
    Optional<PSRelationResponseDto> findByForeignKeys(@Param("productId") Integer productId,
                                                      @Param("supplierId") Integer supplierId);

    // Consulta usando la relacion establecida con JPA, nos permite traer toda la informacion de una relacion
    // evitando la opcion LAZY establecida en las relaciones de entidades.
    @Query("""
            SELECT ps
            FROM ProductSupplier ps
            JOIN FETCH ps.product p
            JOIN FETCH ps.supplier s
            JOIN FETCH p.category c
            JOIN FETCH p.measurement m
            WHERE s.id = :supplierId
           """)
    List<ProductSupplier> findBySupplierId(@Param("supplierId") Integer supplierId);
}