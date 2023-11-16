package com.greenatom.domain.entity;

import com.greenatom.domain.enums.DeliveryType;
import com.greenatom.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * An Order
 */

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "client_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "link_to_folder")
    private String linkToFolder;

    @Column(name = "date_time")
    private Instant orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order")
    private Claim claim;

    @OneToOne(mappedBy = "order")
    private PreparingOrder preparingOrder;

    @OneToMany(mappedBy = "order")
    private Set<Delivery> deliveries;
}
