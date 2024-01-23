package com.greenatom.clientselfservice.service.impl;

import com.greenatom.clientselfservice.domain.dto.CustomPage;
import com.greenatom.clientselfservice.domain.dto.product.ProductResponseDTO;
import com.greenatom.clientselfservice.domain.entity.Product;
import com.greenatom.clientselfservice.domain.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final RedisTemplate<String,Object> redisTemplate;
    private final ProductMapper productMapper;
    private static final String HASH_KEY = "Prduct";

    public Page<ProductResponseDTO> getAll(UriComponentsBuilder builder){
        /*List<ProductResponseDTO> productResponseDTOList =  redisTemplate.opsForHash().values(HASH_KEY).stream()
                .map(i ->productMapper.toDto((Product)i)).toList();*/
        ParameterizedTypeReference<CustomPage<ProductResponseDTO>> responseType = new ParameterizedTypeReference<>() {
        };
        /*if(pageProducts != null && pageProducts.getSize() != 0){
            List<ProductResponseDTO> products = pageProducts.toList();
            for (ProductResponseDTO productResponseDTO : products) {
                Product product = productMapper.toEntity(productResponseDTO);
                redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
            }
        }*/
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET,null, responseType).getBody();
        /*return new PageImpl<>(productResponseDTOList);*/
    }

    public ProductResponseDTO getOne(Long id){
        /*Product product = (Product) redisTemplate.opsForHash().get(HASH_KEY,id);*/

        Product product = productMapper.toEntity(restTemplate.getForObject(getUrl("/"+ id),ProductResponseDTO.class));

        /*redisTemplate.opsForHash().put(HASH_KEY,id,product);*/

        return productMapper.toDto(product);
    }

    private String getUrl (String action){
        return "http://Crm-Service/api/products" + action;
    }

}

