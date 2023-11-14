package com.greenatom.clientselfservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    private Long id;

    @NonNull
    private String productName;

    private String unit;

    private Long storageAmount;

    private Long cost;

    private List<OrderItem> orderItems;

}
