package com.greenatom.clientselfservice.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    private Long id;

    private String name;

    private String unit;

    private Long cost;

    private Long orderAmount;

    private Product product;

    private Order order;

    public long getTotalCost() {
        return cost * orderAmount;
    }

}
