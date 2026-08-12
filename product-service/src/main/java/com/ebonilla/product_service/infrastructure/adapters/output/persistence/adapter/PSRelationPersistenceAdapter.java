package com.ebonilla.product_service.infrastructure.adapters.output.persistence.adapter;

import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.application.ports.output.IPSRelationPort;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.mapper.ProductSupplierMapper;
import com.ebonilla.product_service.infrastructure.adapters.output.persistence.repository.IProductSupplier;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PSRelationPersistenceAdapter implements IPSRelationPort {

    private final EntityManager em;
    private final IProductSupplier psJpa;
    private final JsonMapper mapper;

    @Override
    public Optional<PSRelationResponseDto> findByForeignKeys(Integer productId, Integer supplierId) {
        return psJpa.findByForeignKeys(productId, supplierId);
    }

    @Override
    public Optional<SPSuppliersByFindProductDto> spSuppliersByProduct(Integer productId) {

        Query query = em.createNativeQuery(
                "SELECT fun_get_suppliers_of_product(:p_product_id)::text"
        );

        query.setParameter("p_product_id", productId);

        Object result = query.getSingleResult();

        if (result == null)
            return Optional.empty();

        try {
            String json = result.toString();

            SPSuppliersByFindProductDto response = mapper.readValue(json, SPSuppliersByFindProductDto.class);

            return Optional.of(response);

        } catch (JacksonException e) {
            throw new RuntimeException("Error parsing response: " + e);
        }
    }

    @Override
    public List<ProductByFindSupplierDto> productBySupplier(Integer supplierId) {
        return psJpa.findBySupplierId(supplierId)
                .stream()
                .map(ProductSupplierMapper::productByFindSupplierDto)
                .toList();
    }
}
