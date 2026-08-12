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
public class SPSupplierResponseDto {

    private String supplierName;
    private String phone;
    private String email;
    private BigDecimal price;

    @JsonCreator
    public SPSupplierResponseDto(@JsonProperty("supplier_name") String supplierName,
                                 @JsonProperty("supplier_phone") String phone,
                                 @JsonProperty("supplier_email") String email,
                                 @JsonProperty("price") BigDecimal price) {
        this.supplierName = supplierName;
        this.phone = phone;
        this.email = email;
        this.price = price;
    }
}
