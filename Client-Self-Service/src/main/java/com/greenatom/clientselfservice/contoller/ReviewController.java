package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.contoller.api.ReviewApi;
import com.greenatom.clientselfservice.domain.dto.review.ReviewRequestDTO;
import com.greenatom.clientselfservice.domain.dto.review.ReviewResponseDTO;
import com.greenatom.clientselfservice.domain.enums.ReviewStatus;
import com.greenatom.clientselfservice.domain.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Этот код - контроллер, обрабатывающий запросы API для управления клиентами.
 * Он предоставляет GET и PUT методы для выполнения следующих операций:
 *
 * <p>– GET /get/{id} - возвращает информацию о клиенте с указанным идентификатором
 * <p>– PUT /update - обновляет информацию о клиенте, переданную в запросе тела
 * <p>– POST /add - добавляет нового клиента, используя данные из запроса тела
 *
 * <p>Эти операции выполняются при помощи сервиса ReviewService, реализующего бизнес-логику.
 *
 * @author Максим Быков
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {
    private final RestTemplate restTemplate;

    private final ReviewMapper reviewMapper;

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ReviewResponseDTO> findOne(@PathVariable Long id) {
        ReviewResponseDTO review = Objects.requireNonNull(
                restTemplate.getForObject(getUrl("/"+id),
                        ReviewResponseDTO.class));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(review);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id,
                                                          @RequestBody ReviewRequestDTO review) {
        review.setReviewStatus(ReviewStatus.CREATED);
        Objects.requireNonNull(restTemplate.patchForObject(getUrl("/"+id), review,
                ReviewRequestDTO.class));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewMapper.toResponse(review));
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody ReviewRequestDTO review) {
        Objects.requireNonNull(restTemplate.postForObject(getUrl(""), review,
                ReviewRequestDTO.class));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewMapper.toResponse(review));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        restTemplate.delete(getUrl("/"+id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private String getUrl (String action){
        return "http://CaseLabJavaCrm/api/reviews" + action;
    }
}
