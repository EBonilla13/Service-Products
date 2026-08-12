package com.ebonilla.product_service.application.dto.storedprocedure.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SPProductResponseDto {

    private Integer id;
    private String productName;
    private String model;
    private String spec;
    private String category;
    private String unit;
    private BigDecimal price;

    @JsonCreator
    public SPProductResponseDto(@JsonProperty("id") Integer id,
                                @JsonProperty("name") String productName,
                                @JsonProperty("model") String model,
                                @JsonProperty("specification") String spec,
                                @JsonProperty("category") String category,
                                @JsonProperty("unit") String unit,
                                @JsonProperty("price") BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.model = model;
        this.spec = spec;
        this.category = category;
        this.unit = unit;
        this.price = price;
    }
}
