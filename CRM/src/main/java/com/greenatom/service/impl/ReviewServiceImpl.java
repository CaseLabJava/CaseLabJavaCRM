package com.greenatom.service.impl;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.review.ReviewRequestDTO;
import com.greenatom.domain.dto.review.ReviewResponseDTO;
import com.greenatom.domain.dto.review.ReviewSearchCriteria;
import com.greenatom.domain.entity.Product;
import com.greenatom.domain.entity.Review;
import com.greenatom.domain.enums.ReviewStatus;
import com.greenatom.domain.mapper.ReviewMapper;
import com.greenatom.exception.ReviewException;
import com.greenatom.repository.ProductRepository;
import com.greenatom.repository.ReviewRepository;
import com.greenatom.repository.criteria.ReviewCriteriaRepository;
import com.greenatom.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ReviewServiceImpl является сервисом для работы с отзывами. Он использует репозиторий
 * ReviewRepository
 * для доступа к данным, преобразует их в формат DTO с помощью ReviewMapper и возвращает список отзывов или
 * конкретный отзыв по его ID.
 *
 * @author Максим Быков
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewCriteriaRepository reviewCriteriaRepository;
    private final ReviewMapper reviewMapper;

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponseDTO> findAll(EntityPage entityPage,
                                           ReviewSearchCriteria reviewSearchCriteria) {
        return reviewCriteriaRepository.findAllWithFilters(entityPage,
                reviewSearchCriteria).map(reviewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDTO findOne(Long id) {
        return reviewMapper.toDto(reviewRepository
                .findById(id)
                .orElseThrow(ReviewException.CODE.NO_SUCH_REVIEW::get));
    }

    @Override
    @Transactional
    public ReviewResponseDTO save(ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewMapper.toEntity(reviewRequestDTO);
        updateRating(reviewRequestDTO.getOrderItemId());
        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

    private void updateRating(Long orderItemId) {
        Product product = productRepository.findByReviewId(orderItemId);
        List<Review> reviews = reviewRepository.findByProduct(product.getId());
        double rating = 0.0;
        for (Review r : reviews){
            if (!r.getReviewStatus().equals(ReviewStatus.DISAPPROVED)){
                rating+=r.getReviewMark();
            }
        }
        product.setRating(rating/reviews.size());
        productRepository.save(product);
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        return reviewRepository
                .findById(id)
                .map(existingEvent -> {
                    reviewMapper.partialUpdate(existingEvent,
                            reviewMapper.toResponse(reviewRequestDTO));
                    return existingEvent;
                })
                .map(reviewRepository::save)
                .map(reviewMapper::toDto).orElseThrow(
                        ReviewException.CODE.NO_SUCH_REVIEW::get);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        reviewRepository
                .findById(id)
                .ifPresent(reviewRepository::delete);
    }

    @Override
    @Transactional
    public ReviewResponseDTO process(Long id, ReviewStatus status,
                                     ReviewRequestDTO reviewDTO) {
        Review review = reviewRepository.findById(id).orElseThrow(
                ReviewException.CODE.NO_SUCH_REVIEW::get);
        if (!review.getReviewStatus().equals(ReviewStatus.CREATED)) {
            throw ReviewException.CODE.NO_SUCH_REVIEW.get();
        }
        if (reviewDTO != null) {
            review.setContent(reviewDTO.getContent());
            review.setImage(reviewDTO.getImage());
        }
        reviewRepository.save(review);
        return reviewMapper.toDto(review);
    }

}
