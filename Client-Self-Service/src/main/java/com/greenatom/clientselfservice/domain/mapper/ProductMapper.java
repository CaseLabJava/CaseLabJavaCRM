package com.greenatom.clientselfservice.domain.mapper;

import com.greenatom.clientselfservice.domain.dto.product.ProductResponseDTO;
import com.greenatom.clientselfservice.domain.entity.Product;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductResponseDTO, Product>{
    ProductResponseDTO toDto(Product s);

    Product toEntity(ProductResponseDTO s);

    List<Product> toEntity(List<ProductResponseDTO> s);

    List<ProductResponseDTO> toDto(List<Product> s);
}
