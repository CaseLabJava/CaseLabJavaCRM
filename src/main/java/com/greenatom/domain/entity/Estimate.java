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
@Table(name = "estimate")
public class Estimate {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "estimate_id")
    private Long id;


    @Column(name = "name_product")
    @NonNull
    private String productName;


    @Column(name = "unit")
    @NonNull
    private String unit;


    @Column(name = "count")
    private Long count;

    @Column(name = "price")
    private Long price;
}
