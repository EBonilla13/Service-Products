package com.ebonilla.product_service.application.dto.measurement.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MeasurementRequestDto {

    @Schema(
            description = "ID de unidad de medida",
            example = "34"
    )
    private Integer id;

    @Schema(
            description = "Unidad de medida",
            example = "Kilogramo",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "field unit cannot be null")
    private String unit;

    @Schema(
            description = "Unidad de medidad abreviada",
            example = "kg",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "field symbol cannot be null")
    private String symbol;

}
