package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract;

import com.ebonilla.product_service.application.dto.product.request.ProductRequestDto;
import com.ebonilla.product_service.application.dto.product.response.ProductResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Productos", description = "Gestion de productos")
public interface ProductApi {

    @Operation(
            summary = "Crea un nuevo producto",
            description = "Devuelve un nuevo producto con ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PostMapping
    ResponseEntity<BaseResponse<ProductResponseDto>> create(
            @Parameter(description = "Informacion de producto, sin ID")
            @Valid @RequestBody ProductRequestDto request);

    @Operation(
            summary = "Actualiza un producto existente",
            description = "Devuelve producto actualizado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PutMapping
    ResponseEntity<BaseResponse<ProductResponseDto>> update(
            @Parameter(description = "Requiere todos las propiedades del producto")
            @Valid @RequestBody ProductRequestDto request);

    @Operation(
            summary = "Obtiene un producto por ID",
            description = "Devuelve la informacion de producto por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones del parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/id")
    ResponseEntity<BaseResponse<ProductResponseDto>> findById(
            @Parameter(description = "Requiere ID", example = "2")
            @RequestParam @Min(value = 0, message = "Id must be positive number") Integer id);

    @Operation(
            summary = "Obtiene un producto por su nombre",
            description = "Devuelve la informacion de un producto mediante su nombre"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones del parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/name")
    ResponseEntity<BaseResponse<ProductResponseDto>> findByName(
            @Parameter(description = "Requiere un nombre")
            @RequestParam @NotBlank(message = "field name is required") String name);

    @Operation(
            summary = "Obtiene un producto por su modelo",
            description = "Devuelve la informacion de un producto mediante su modelo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones del parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/model")
    ResponseEntity<BaseResponse<ProductResponseDto>> findByModel(
            @Parameter(description = "Requiere un modelo de producto")
            @RequestParam @NotBlank(message = "field model is required") String model);

    @Operation(
            summary = "Obtiene un producto por su especificacion",
            description = "Devuelve la informacion de un producto mediante su especificacion"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones del parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/specification")
    ResponseEntity<BaseResponse<ProductResponseDto>> findBySpecification(
            @Parameter(description = "Requiere la especificacion de un producto")
            @RequestParam @NotBlank(message = "specification is required") String specification);

    @Operation(
            summary = "Obtiene productos",
            description = "Devuelve una lista de productos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "204", description = "No hay productos ni respuesta"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/show")
    ResponseEntity<List<ProductResponseDto>> products();

    @Operation(
            summary = "Obtiene productos por su categoryId",
            description = "Devuelve una lista de productos mediante su ID de categoria"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/show/categoryId")
    ResponseEntity<List<ProductResponseDto>> allProductsByCategoryId(
            @Parameter(description = "Requiere ID de categoria valido", example = "23")
            @RequestParam @Min(value = 0, message = "category Id must be positive number") Integer categoryId
    );
}
