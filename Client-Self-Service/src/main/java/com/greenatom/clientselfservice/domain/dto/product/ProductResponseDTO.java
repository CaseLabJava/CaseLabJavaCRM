package com.greenatom.clientselfservice.domain.dto.product;

import com.greenatom.clientselfservice.domain.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO implements Serializable {
    private Long id;

    private String productName;

    private String unit;

    private Long storageAmount;

    private Long cost;

    private List<OrderItem> orderItems;

}
