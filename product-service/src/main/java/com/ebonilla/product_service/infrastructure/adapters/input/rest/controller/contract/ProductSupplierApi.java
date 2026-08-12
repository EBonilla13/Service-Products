package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract;

import com.ebonilla.product_service.application.dto.productsupplier.request.PSRelationRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.request.ProductSupplierRequestDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.PSRelationResponseDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductByFindSupplierDto;
import com.ebonilla.product_service.application.dto.productsupplier.response.ProductSupplierResponseDto;
import com.ebonilla.product_service.application.dto.storedprocedure.response.SPSuppliersByFindProductDto;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Productos-Proveedores", description = "Gestion de tabla pivote productos proveedores")
public interface ProductSupplierApi {

    @Operation(
            summary = "Crea un nueva relacion de producto y proveedor",
            description = "Devuelve la informacion de la relacion con ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PostMapping
    ResponseEntity<BaseResponse<ProductSupplierResponseDto>> create(
            @Valid @RequestBody ProductSupplierRequestDto request);

    @Operation(
            summary = "Crea product, proveedor y relacion",
            description = "Devuelve informacion de nueva relacion"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PostMapping("/relation")
    ResponseEntity<BaseResponse<PSRelationResponseDto>> createRelation(
            @Parameter(description = "Requiere producto, proveedor y precio")
            @Valid @RequestBody PSRelationRequestDto request);

    @Operation(
            summary = "Actualiza la relacion de producto proveedor",
            description = "Devuelve informacion actualizada de la relacion"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @PutMapping
    ResponseEntity<BaseResponse<ProductSupplierResponseDto>> update(
            @Parameter(description = "Requiere toda la informacion de la relacion incluyendo ID")
            @Valid @RequestBody ProductSupplierRequestDto request
    );

    @Operation(
            summary = "Obtiene un producto-proveedor por ID",
            description = "Devuelve la informacion de un producto proveedor mediante su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Error de parametro"),
            @ApiResponse(responseCode = "401", description = "Token no valido"),
            @ApiResponse(responseCode = "403", description = "Sin permiso para el endpoint")
    })
    @GetMapping("/id/{id}")
    ResponseEntity<BaseResponse<ProductSupplierResponseDto>> findById(
            @Parameter(description = "Requiere ID de relacion", example = "23")
            @PathVariable @Min(value = 0, message = "Id cannot be negative number") Integer id);

    @Operation(
            summary = "Obtiene relacion por ID de producto y ID de proveedor",
            description = "Devuelve informacion de la relacion mediante sus llaves foraneas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de parametros"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/relation")
    @Parameters({
            @Parameter(name = "ID de producto", description = "Identificador de producto", example = "23"),
            @Parameter(name = "ID de proveedor", description = "Identificador de proveedor", example = "56")
    })
    ResponseEntity<BaseResponse<PSRelationResponseDto>> findByForeignKeys(
            @RequestParam @NotNull(message = "product id cannot be null")
            @Min(value = 0, message = "invalid number for product ID") Integer productId,
            @RequestParam @NotNull(message = "supplier id cannot be null")
            @Min(value = 1, message = "invalid number for supplier ID") Integer supplierId
    );

    @Operation(
            summary = "Obtiene proveedores por ID de producto",
            description = "Devuelve JSON con un producto y una lista de proveedores"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validacion de parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/supplier/{productId}")
    ResponseEntity<BaseResponse<SPSuppliersByFindProductDto>> suppliersByProductId(
            @Parameter(description = "Requiere ID de producto", required = true)
            @PathVariable @Min(value = 0, message = "invalid number for product ID") Integer productId
    );

    @Operation(
            summary = "Obtiene productos por el ID de proveedor",
            description = "Devuelve una lista con el producto y los proveedores mediante el ID de proveedor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validacion de parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para este endpoint")
    })
    @GetMapping("/products/{supplierId}")
    ResponseEntity<BaseResponse<List<ProductByFindSupplierDto>>> productsBySupplierId(
            @Parameter(description = "Requiere ID de proveedor", example = "34")
            @PathVariable @Min(value = 0, message = "invalid number for supplier ID") Integer supplierId
    );
}
