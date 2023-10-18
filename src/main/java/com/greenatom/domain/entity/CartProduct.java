package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * A CartProduct.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "cart_product")
public class CartProduct {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "cart_product_id")
    private Long id;


    @Column(name = "request_id")
    @NonNull
    private Long requestId;

    @Column(name = "product_id")
    @NonNull
    private Long productId;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "unit")
    @NonNull
    private String unit;

    @Column(name = "cost")
    @NonNull
    private Long cost;

    @Column(name = "request_amount")
    @NonNull
    private Long requestAmount;

}
