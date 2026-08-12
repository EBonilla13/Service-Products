package com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Supplier extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "supplier_name", nullable = false, length = 100)
    private String name;

    @Column(name = "number_phone", length = 20)
    private String numberPhone;

    @Column(name = "email", length = 100)
    private String email;

}
