package com.ebonilla.product_service.application.dto.supplier.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SupplierRequestDto {

    @Schema(
            description = "ID de proveedor",
            example = "42"
    )
    private Integer id;

    @Schema(
            description = "Nombre de proveedor",
            example = "ferreteria ejemplar",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Supplier name is required")
    private String name;

    @Schema(
            description = "Numero de telefono de proveedor(10 0 12 digitos)",
            example = "8112131234"
    )
    @Length(min = 10, max = 12, message = "Invalid length for number phone")
    private String phone;

    @Schema(
            description = "Correo electronico de proveedor",
            example = "correo_de_ejemplo@email.com"
    )
    private String email;

}
