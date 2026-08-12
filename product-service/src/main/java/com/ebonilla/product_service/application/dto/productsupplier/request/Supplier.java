package com.ebonilla.product_service.application.dto.productsupplier.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Supplier {

    @Schema(
            description = "ID de proveedor",
            example = "21"
    )
    private Integer id;

    @Schema(
            description = "Nombre de proveedor",
            example = "tienda",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Suppler name cannot be null")
    private String name;

    @Schema(
            description = "Numero telefonico de proveedor(10 o 12 digitos)",
            example = "8212342134"
    )
    @NotNull(message = "Category phone cannot be null")
    private String phone;

    @Schema(
            description = "Correo electronico",
            example = "ejemplo@email.com"
    )
    private String email;

}
