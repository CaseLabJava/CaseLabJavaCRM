package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * A Client.
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    @NonNull
    private String productName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "storage_amount")
    private Long storageAmount;

    @Column(name = "cost")
    private Long cost;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
