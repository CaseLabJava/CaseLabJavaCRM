package com.greenatom.controller;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.review.ReviewRequestDTO;
import com.greenatom.domain.dto.review.ReviewResponseDTO;
import com.greenatom.domain.dto.review.ReviewSearchCriteria;
import com.greenatom.domain.enums.ReviewStatus;
import com.greenatom.service.ReviewService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ReviewResponseDTO> findOne(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findOne(id));
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Page<ReviewResponseDTO>> findAll(
            @RequestParam(defaultValue = "0")
            @Min(value = 0) Integer pagePosition,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) byte[] image,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Integer reviewMark,
            @RequestParam(required = false) String reviewStatus,
            @RequestParam(required = false) Long orderItemId,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findAll(
                        new EntityPage(pagePosition, pageSize, sortDirection, sortBy),
                        new ReviewSearchCriteria(
                                0L, content,
                                image,
                                clientId,
                                orderItemId,
                                reviewMark,
                                ReviewStatus.valueOf(reviewStatus))));
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id,
                                                          @RequestBody ReviewRequestDTO review) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.updateReview(id, review));
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody ReviewRequestDTO review) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.save(review));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
