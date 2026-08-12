package com.ebonilla.product_service.application.dto.productsupplier.response;

import lombok.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProductByFindSupplierDto {

    private final Integer productId;
    private final String productName;
    private final String productModel;
    private final String productSpecification;
    private final String categoryName;
    private final String symbol;
    private final Integer psId;
    private final BigDecimal price;
    private final String supplierName;
    private final String phone;
    private final String email;

}
