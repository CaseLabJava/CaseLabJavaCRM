package com.greenatom.clientselfservice.domain.dto.product;

import com.greenatom.clientselfservice.domain.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;

    private String productName;

    private String unit;

    private Long storageAmount;

    private Long cost;

    private List<OrderItem> orderItems;

}
