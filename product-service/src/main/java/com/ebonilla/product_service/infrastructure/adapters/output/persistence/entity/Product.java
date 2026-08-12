package com.ebonilla.product_service.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String name;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "specification", length = 100)
    private String specification;

    // Relacion M-1 con tabla categoria (tabla productos tiene una referencia de la tabla categoria)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false)
    private Category category;

    // Relacion M-1 con tabla unidad de medida (tabla productos tiene la referencia de la tabla unidad)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_measurement", referencedColumnName = "id", nullable = false)
    private Measurement measurement;

}
