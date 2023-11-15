package com.greenatom.clientselfservice.domain.entity;

import com.greenatom.clientselfservice.domain.enums.ClaimStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public class Claim {

    private Long id;

    @NonNull
    private Order order;

    @NonNull
    private Client client;

    private ClaimStatus claimStatus;

    private Instant creationTime;

    private Instant resolvedTime;
}
