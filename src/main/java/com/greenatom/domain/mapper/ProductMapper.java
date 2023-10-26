package com.greenatom.domain.mapper;

import com.greenatom.domain.dto.ProductDTO;
import com.greenatom.domain.entity.Product;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Product} and its DTO called {@link ProductDTO}.
 */

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    ProductDTO toDto(Product s);

    Product toEntity(ProductDTO s);

}
