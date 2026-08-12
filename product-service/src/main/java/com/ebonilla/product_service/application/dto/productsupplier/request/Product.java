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
public class Product {

    @Schema(
            description = "ID de producto",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer id;

    @Schema(
            description = "Nombre de producto",
            example = "lavadora",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Product name cannot be null")
    private String name;

    @Schema(
            description = "Modelo de producto",
            example = "lavafacil",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Product model cannot be null")
    private String model;

    @Schema(
            description = "Especificacion de producto(Opcional)",
            example = "15 Kg"
    )
    private String spec;

    @Schema(
            description = "ID de categoria",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Category Id cannot be null")
    private Integer categoryId;

    @Schema(
            description = "ID de unidad de medida",
            example = "6",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Measurement Id cannot be null")
    private Integer measurementId;
}
