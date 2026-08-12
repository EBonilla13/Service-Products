package com.ebonilla.product_service.application.dto.productsupplier.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductSupplierRequestDto {

    @Schema(
            description = "ID de relacion producto-proveedor",
            example = "22"
    )
    private Integer id;

    @Schema(
            description = "ID de producto",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "product id cannot be null")
    private Integer productId;

    @Schema(
            description = "ID de proveedor",
            example = "4",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "supplier id cannot be null")
    private Integer supplierId;

    @Schema(
            description = "Precio de producto",
            example = "299.00",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "price cannot be null")
    private BigDecimal price;

}


