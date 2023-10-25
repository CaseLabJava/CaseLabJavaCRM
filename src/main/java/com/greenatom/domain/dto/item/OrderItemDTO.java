package com.greenatom.domain.dto.item;

import com.greenatom.domain.entity.Order;
import com.greenatom.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
     * A DTO for the Cart Product.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;

    private Order order;

    private Product product;

    private String name;

    private String unit;

    private Long cost;

    private Long orderAmount;

}
