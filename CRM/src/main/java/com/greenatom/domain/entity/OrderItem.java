package com.greenatom.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * An Order Item
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 1, max = 200)
    private String name;

    @Column(name = "unit")
    @NotBlank
    @Size(min = 1, max = 20)
    private String unit;

    @Column(name = "cost")
    @Min(0)
    private Long cost;

    @Column(name = "amount")
    @Min(0)
    private Long orderAmount;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    public long getTotalCost() {
        return cost * orderAmount;
    }

}
