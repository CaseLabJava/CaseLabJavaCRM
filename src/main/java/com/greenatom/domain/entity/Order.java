package com.greenatom.domain.entity;

import com.greenatom.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;

/**
 * A Order.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "client_order")
public class Order {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "order_id")
    private Long id;

    @Column(name = "link_to_folder")
    private String linkToFolder;

    @Column(name = "date_time")
    private Date orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
