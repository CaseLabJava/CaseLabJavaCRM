package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import com.greenatom.domain.entity.Product;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Product} and its DTO called {@link ProductResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductResponseDTO, Product> {
    ProductResponseDTO toDto(Product s);

    Product toEntity(ProductResponseDTO s);

    Product toEntity(ProductRequestDTO s);

    ProductResponseDTO toResponse(ProductRequestDTO s);

}
