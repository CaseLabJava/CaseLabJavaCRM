package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.domain.dto.product.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("self_service/product")
@RequiredArgsConstructor
public class ProductController {

    private final RestTemplate restTemplate;

    @GetMapping("/get")
    public List<ProductResponseDTO> findAll(){

        return Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(getUrl(), ProductResponseDTO[].class))).toList();
    }

    private String getUrl (){
        return "http://CaseLabJavaCrm/api/products/get";
    }
}
