package com.greenatom.domain.mapper;
import com.greenatom.domain.dto.CartProductDTO;
import com.greenatom.domain.entity.CartProduct;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CartProduct} and its DTO called {@link CartProductDTO}.
 */

@Mapper(componentModel = "spring")
public interface CartProductMapper extends EntityMapper<CartProductDTO, CartProduct> {
    CartProductDTO toDto(CartProduct s);

    CartProduct toEntity(CartProductDTO s);

}
