package com.greenatom.service;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.review.ReviewRequestDTO;
import com.greenatom.domain.dto.review.ReviewResponseDTO;
import com.greenatom.domain.dto.review.ReviewSearchCriteria;
import com.greenatom.domain.enums.ReviewStatus;
import org.springframework.data.domain.Page;

public interface ReviewService {

    Page<ReviewResponseDTO> findAll(EntityPage entityPage, ReviewSearchCriteria clientSearchCriteria);

    ReviewResponseDTO findOne(Long id);

    ReviewResponseDTO save(ReviewRequestDTO client);

    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO client);

    void deleteReview(Long id);

    ReviewResponseDTO process(Long id, ReviewStatus status, ReviewRequestDTO review);
}
