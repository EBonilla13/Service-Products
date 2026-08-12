package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract;

import com.ebonilla.product_service.application.dto.supplier.request.SupplierRequestDto;
import com.ebonilla.product_service.application.dto.supplier.response.SupplierResponseDto;
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

@Tag(name = "Proveedores", description = "Gestion de proveedores")
public interface SupplierApi {

    @Operation(
            summary = "Crea un nuevo proveedor",
            description = "Devuelve un nuevo proveedor con ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PostMapping
    ResponseEntity<BaseResponse<SupplierResponseDto>> create(
            @Parameter(description = "Require informacion de proveedor, sin ID")
            @Valid @RequestBody SupplierRequestDto request);

    @Operation(
            summary = "Actualiza un proveedor existente",
            description = "Devuelve un proveedor actualizado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PutMapping
    ResponseEntity<BaseResponse<SupplierResponseDto>> update(
            @Parameter(description = "Requiere toda la informacion de un proveedor")
            @Valid @RequestBody SupplierRequestDto request);

    @Operation(
            summary = "Obtiene un proveedor por su ID",
            description = "Devuelve un proveedor mediante su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/id/{id}")
    ResponseEntity<BaseResponse<SupplierResponseDto>> findById(
            @Parameter(description = "Requiere ID de proveedor", example = "234")
            @PathVariable @Min(value = 0, message = " supplier id cannot be negative number") Integer id
    );

    @Operation(
            summary = "Obtiene un proveedor por su nombre",
            description = "Devuelve un proveedor mediante su nombre"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/name")
    ResponseEntity<BaseResponse<SupplierResponseDto>> findByName(
            @Parameter(description = "Requiere el nombre de proveedor")
            @RequestParam @NotBlank(message = "supplier name is required") String name
    );

    @Operation(
            summary = "Obtiene todos los proveedores",
            description = "Devuelve una lista de proovedores"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/show")
    ResponseEntity<BaseResponse<List<SupplierResponseDto>>> suppliers();
}
