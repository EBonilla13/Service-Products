package com.ebonilla.product_service.application.dto.productsupplier.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PSRelationRequestDto {

    @Schema(
            description = "Informacion de producto",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Product cannot be null")
    private Product product;

    @Schema(
            description = "Informacion de proveedor",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Supplier cannot be null")
    private Supplier supplier;

    @NotNull(message = "Price cannot be null")
    private RelationRequestDto relation;

}
