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
public class RelationRequestDto {

    @Schema(
            description = "Precio de producto",
            example = "2599.99",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

}
