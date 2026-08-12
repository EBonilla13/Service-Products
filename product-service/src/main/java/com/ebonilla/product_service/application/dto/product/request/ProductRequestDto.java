package com.ebonilla.product_service.application.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductRequestDto {

    @Schema(
            description = "ID de producto",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Nombre de producto",
            example = "lapiz",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Product name cannot be null")
    private String name;

    @Schema(
            description = "Modelo de producto",
            example = "Castell 9000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Product model cannot be null")
    private String model;

    @Schema(
            description = "Especificacion de producto (Opcional)",
            example = "NB/Numero 2"
    )
    private String specification;

    @Schema(
            description = "ID de categoria",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer categoryId;

    @Schema(
            description = "ID de unidad de medida",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer measurementId;
}
