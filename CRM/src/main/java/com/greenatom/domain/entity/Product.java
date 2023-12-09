package com.greenatom.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * A Product
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
    @Size(min = 1, max = 200)
    private String productName;

    @Column(name = "unit")
    @NotBlank
    @Size(min = 1, max = 20)
    private String unit;

    @Column(name = "storage_amount")
    @Min(0)
    private Long storageAmount;

    @Column(name = "cost")
    @Min(0)
    private Long cost;

    @Column(name = "image")
    private byte[] image;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

}
