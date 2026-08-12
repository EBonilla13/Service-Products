package com.ebonilla.product_service.infrastructure.adapters.input.rest.controller.contract;

import com.ebonilla.product_service.application.dto.category.request.CategoryRequestDto;
import com.ebonilla.product_service.application.dto.category.response.CategoryResponseDto;
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

@Tag(name = "Categorias", description = "Gestion de categorias")
public interface CategoryApi {

    @Operation(
            summary = "Crear nueva categoria",
            description = "Devuelve nueva categoria con su identificador unico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en el DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para el endpoint")
    })
    @PostMapping
    ResponseEntity<CategoryResponseDto> createCategory(
            @Parameter(description = "Informacion de categoria nueva sin agregar ID")
            @Valid @RequestBody CategoryRequestDto request);

    @Operation(
            summary = "Actualiza una categoria",
            description = "Devuelve categoria actualizada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en el DTO"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para el endpoint")
    })
    @PutMapping
    ResponseEntity<CategoryResponseDto> updateCategory(
            @Parameter(description = "Informacion de categoria existente")
            @Valid @RequestBody CategoryRequestDto request);

    @Operation(
            summary = "Obtiene la categoria por ID",
            description = "Devuelve categoria buscada por ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Error de validacion del parametro"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para el endpoint")
    })
    @GetMapping("/id")
    ResponseEntity<CategoryResponseDto> findById(
            @Parameter(description = "Identificador de categoria", example = "10")
            @RequestParam @Min(value = 0, message = "Id cannot be negative") Integer idCategory);

    @Operation(
            summary = "Obtiene todos las categorias",
            description = "Devuelve una lista de categorias"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "204", description = "No hay categorias"),
            @ApiResponse(responseCode = "401", description = "Token invalido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para el endpoint")
    })
    @GetMapping("/show")
    ResponseEntity<List<CategoryResponseDto>> findAllCategories();
}
