package com.greenatom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
     * A DTO for the Supply.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplyDTO {

    private Long id;

    private String deliveryPrice;

    private String materialName;

    private Long providerId;
}
