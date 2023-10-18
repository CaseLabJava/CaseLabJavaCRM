package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * A Client.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "product_id")
    private Long id;


    @Column(name = "name")
    @NonNull
    private String productName;


    @Column(name = "unit")
    @NonNull
    private String unit;

    @Column(name = "storage_amount")
    private Long storageAmount;

    @Column(name = "cost")
    private Long cost;
}
