package com.greenatom.domain.entity;

import com.greenatom.domain.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * A Delivery
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @NonNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "courier_id")
    private Courier courier;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

}
