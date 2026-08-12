package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract;

import com.ebonilla.product_service.application.dto.measurement.request.MeasurementRequestDto;
import com.ebonilla.product_service.application.dto.measurement.response.MeasurementResponseDto;
import com.ebonilla.product_service.infrastructure.adapters.input.rest.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Unidades de medida", description = "Gestion de unidades de medida")
public interface MeasurementApi {

    @Operation(
            summary = "Crear nueva unidad de medida",
            description = "Devuelve nueva unidad de medida con ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para el endpoint")
    })
    @PostMapping
    ResponseEntity<BaseResponse<MeasurementResponseDto>> create(
            @Parameter(description = "Informacion de nueva unidad, no require ID")
            @Valid @RequestBody MeasurementRequestDto request);

    @Operation(
            summary = "Actualiza una unidad de medida existente",
            description = "Devuelve unidad de medida actualizada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para el endpoint")
    })
    @PutMapping
    ResponseEntity<BaseResponse<MeasurementResponseDto>> update(
            @Parameter(description = "Informacion de unidad de medida")
            @Valid @RequestBody MeasurementRequestDto request);

    @Operation(
            summary = "Obtiene una unidad de medida por ID",
            description = "Devuelve unidad de medida"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validaciones de DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para el endpoint")
    })
    @GetMapping("/id/{id}")
    ResponseEntity<BaseResponse<MeasurementResponseDto>> findById(
            @Parameter(description = "Identificador de unidad de medida", example = "5")
            @PathVariable @Min(value = 0, message = "Id cannot be negative number") Integer id);

    @Operation(
            summary = "Obtiene todos las unidades de medida",
            description = "Devuelve una lista con las unidades de medida"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "204", description = "No hay unidades de medida"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para el endpoint")
    })
    @GetMapping("show")
    ResponseEntity<BaseResponse<List<MeasurementResponseDto>>> measurements();

    @Operation(
            summary = "Elimina una unidad de medida por ID",
            description = "No devuelve nada al eliminar la unidad de medida"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sin respuesta"),
            @ApiResponse(responseCode = "400", description = "Validacion de parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "Permiso no valido para el endpoint")
    })
    @DeleteMapping("/id")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID de unidad de medida", example = "12")
            @RequestParam @Min(value = 0, message = "Id cannot be negative") Integer id);
}
