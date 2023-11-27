package com.greenatom.clientselfservice.service.impl;

import com.greenatom.clientselfservice.domain.dto.product.ProductResponseDTO;
import com.greenatom.clientselfservice.domain.entity.Product;
import com.greenatom.clientselfservice.domain.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final RedisTemplate<String,Object> redisTemplate;
    private final ProductMapper productMapper;
    private static final String HASH_KEY = "Product";

    public Page<ProductResponseDTO> getAll(UriComponentsBuilder builder){
        List<ProductResponseDTO> productResponseDTOList =  redisTemplate.opsForHash().values(HASH_KEY).stream()
                .map(i ->productMapper.toDto((Product)i)).toList();
        if(productResponseDTOList.isEmpty()){
            ParameterizedTypeReference<Page<ProductResponseDTO>> responseType = new ParameterizedTypeReference<>() {};
            Page<ProductResponseDTO> pageProducts = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,null, responseType).getBody();
            List<ProductResponseDTO> products;
            if(pageProducts != null && pageProducts.getSize() != 0){
                products = pageProducts.toList();
                products.forEach(i -> redisTemplate.opsForHash().put(HASH_KEY,i.getId(),i));
            }
            return pageProducts;
        }
        return new PageImpl<>(productResponseDTOList);
    }

    public ProductResponseDTO getOne(Long id){
        Product product = (Product) redisTemplate.opsForHash().get(HASH_KEY,id);
        if(product == null){
            product = productMapper.toEntity(restTemplate.getForObject(getUrl("/"+ id),ProductResponseDTO.class));
            redisTemplate.opsForHash().put(HASH_KEY,id,product);
        }
        return productMapper.toDto(product);
    }

    private String getUrl (String action){
        return "http://CaseLabJavaCrm/api/products" + action;
    }

}

