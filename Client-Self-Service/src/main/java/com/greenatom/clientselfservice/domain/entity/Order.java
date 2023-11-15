package com.greenatom.clientselfservice.domain.entity;

import com.greenatom.clientselfservice.domain.enums.DeliveryType;
import com.greenatom.clientselfservice.domain.enums.OrderStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Order {

    private Long id;

    private Instant orderDate;

    private OrderStatus orderStatus;

    private DeliveryType deliveryType;

    private Client client;

    private List<OrderItem> orderItems;

    private Claim claim;

}
