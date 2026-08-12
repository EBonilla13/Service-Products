package com.ebonilla.product_service.application.dto.category.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryRequestDto {

    @Schema(
            description = "Id de categoria",
            example = "21"
    )
    private Integer id;

    @Schema(
            description = "Nombre de categoria",
            example = "limpieza",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Category name is required")
    private String name;
}
