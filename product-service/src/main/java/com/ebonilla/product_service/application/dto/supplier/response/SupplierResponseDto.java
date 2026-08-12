package com.ebonilla.product_service.application.dto.supplier.response;

import lombok.*;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class SupplierResponseDto {

    private final Integer id;
    private final String name;
    private final String phone;
    private final String email;
    private final Instant createAt;
    private final Instant updatedAt;

    public SupplierResponseDto(Integer id, String name, String phone, String email){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createAt = null;
        this.updatedAt = null;
    }
}
