package com.greenatom.service.impl;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.review.ReviewRequestDTO;
import com.greenatom.domain.dto.review.ReviewResponseDTO;
import com.greenatom.domain.dto.review.ReviewSearchCriteria;
import com.greenatom.domain.entity.Review;
import com.greenatom.domain.mapper.ReviewMapper;
import com.greenatom.exception.ReviewException;
import com.greenatom.repository.ReviewRepository;
import com.greenatom.repository.criteria.ReviewCriteriaRepository;
import com.greenatom.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ReviewServiceImpl является сервисом для работы с отзывами. Он использует репозиторий
 * ReviewRepository
 * для доступа к данным, преобразует их в формат DTO с помощью ReviewMapper и возвращает список отзывов или
 * конкретный отзыв по его ID.
 * @author Максим Быков
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository clientRepository;
    private final ReviewCriteriaRepository clientCriteriaRepository;
    private final ReviewMapper clientMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponseDTO> findAll(EntityPage entityPage, ReviewSearchCriteria clientSearchCriteria) {
        return clientCriteriaRepository.findAllWithFilters(entityPage, clientSearchCriteria).map(clientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDTO findOne(Long id) {
        return clientMapper.toDto(clientRepository
                .findById(id)
                .orElseThrow(ReviewException.CODE.NO_SUCH_REVIEW::get));
    }

    @Override
    @Transactional
    public ReviewResponseDTO save(ReviewRequestDTO clientRequestDTO) {
        Review client = clientMapper.toEntity(clientRequestDTO);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO clientRequestDTO) {
        return clientRepository
                .findById(id)
                .map(existingEvent -> {
                    clientMapper.partialUpdate(existingEvent, clientMapper.toResponse(clientRequestDTO));
                    return existingEvent;
                })
                .map(clientRepository::save)
                .map(clientMapper::toDto).orElseThrow(
                        ReviewException.CODE.NO_SUCH_REVIEW::get);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        clientRepository
                .findById(id)
                .ifPresent(clientRepository::delete);
    }

}
