package com.greenatom.domain.entity;

import com.greenatom.domain.enums.PreparingOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * A Preparing Order
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
@Table(name = "preparing_order")
public class PreparingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preparing_order_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @NonNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PreparingOrderStatus preparingOrderStatus;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

}
