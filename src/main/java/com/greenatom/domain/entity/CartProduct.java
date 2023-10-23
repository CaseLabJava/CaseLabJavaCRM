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
@NoArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProduct {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "cart_product_id")
    private Long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "request_amount")
    private Long requestAmount;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "request_id")
    private Request request;

    public long getTotalCost() {
        return cost * requestAmount;
    }

}
