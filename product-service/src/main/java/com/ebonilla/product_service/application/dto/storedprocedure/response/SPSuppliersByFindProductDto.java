package com.ebonilla.product_service.application.dto.storedprocedure.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propiedades que no esten dentro de la clase, en caso de agregar mas a la funcion
public class SPSuppliersByFindProductDto {

    private Integer id;
    private String productName;
    private String model;
    private String specification;
    private String category;
    private String symbol;

    private List<SPSupplierResponseDto> suppliers;

    @JsonCreator
    public SPSuppliersByFindProductDto(@JsonProperty("product_id") Integer id,
                                       @JsonProperty("product_name") String productName,
                                       @JsonProperty("product_model") String model,
                                       @JsonProperty("product_specification") String specification,
                                       @JsonProperty("category") String category,
                                       @JsonProperty("symbol") String symbol,
                                       @JsonProperty("suppliers") List<SPSupplierResponseDto> suppliers) {
        this.id = id;
        this.productName = productName;
        this.model = model;
        this.specification = specification;
        this.category = category;
        this.symbol = symbol;
        this.suppliers = suppliers;
    }
}
