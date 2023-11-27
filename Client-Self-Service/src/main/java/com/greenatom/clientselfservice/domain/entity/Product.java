package com.greenatom.clientselfservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("Product")
public class Product implements Serializable {

    private Long id;

    @NonNull
    private String productName;

    private String unit;

    private Long storageAmount;

    private Long cost;

    private List<OrderItem> orderItems;

}
