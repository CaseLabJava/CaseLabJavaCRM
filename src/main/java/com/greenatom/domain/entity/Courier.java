package com.greenatom.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * A Courier
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courier")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courier_id")
    private Long id;

    @Column(name = "is_active")
    @NonNull
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @NonNull
    private Employee employee;

    @OneToMany(mappedBy = "courier")
    private Set<Delivery> deliveries;

}
