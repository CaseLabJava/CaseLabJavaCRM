package com.greenatom.clientselfservice.domain.entity;

import com.greenatom.clientselfservice.domain.enums.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("Review")
public class Review implements Serializable {

    private Long id;

    private String content;

    private byte[] image;

    private Long clientId;

    private Long orderItemId;

    private Integer reviewMark;

    private ReviewStatus reviewStatus;

    private Instant creationTime;
}
