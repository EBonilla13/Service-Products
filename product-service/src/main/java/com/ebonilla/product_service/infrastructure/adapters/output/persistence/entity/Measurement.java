package com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Table(name = "measurements")
public class Measurement extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "unit", nullable = false, length = 100)
    private String unit;

    @Column(name = "symbol", length = 10)
    private String symbol;

}

