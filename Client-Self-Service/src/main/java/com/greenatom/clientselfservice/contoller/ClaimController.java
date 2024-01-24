package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.domain.dto.claim.ClaimRequestDTO;
import com.greenatom.clientselfservice.domain.dto.claim.ClaimResponseDTO;
import com.greenatom.clientselfservice.domain.mapper.ClaimMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("self-service/claim")
@RequiredArgsConstructor
public class ClaimController {
    private final RestTemplate restTemplate;

    private final ClaimMapper claimMapper;

    @PostMapping
        public ResponseEntity<ClaimResponseDTO> addClaim(@RequestBody ClaimRequestDTO claim) {
        Objects.requireNonNull(restTemplate.postForObject(getUrl(), claim,
                ClaimRequestDTO.class));
        return ResponseEntity.ok(claimMapper.toResponse(claim));
    }
    @GetMapping
    public List<ClaimResponseDTO> findByUser(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Map<String, Integer> uriParam = new HashMap<>();
        uriParam.put("pageSize", pageSize);
        uriParam.put("pageNumber", pageNumber);
        return Arrays.stream(Objects.requireNonNull(
                restTemplate.getForObject(getUrl()
                             //   +"?pageNumber="+pageNumber+"&pageSize" +pageSize
                        ,
                        ClaimResponseDTO[].class,uriParam))).toList();
    }

    private String getUrl() {
        return "http://Crm-Service/api/claims";
    }

}
