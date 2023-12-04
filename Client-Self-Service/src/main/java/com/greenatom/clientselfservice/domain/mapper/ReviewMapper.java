package com.greenatom.clientselfservice.domain.mapper;


import com.greenatom.clientselfservice.domain.dto.review.ReviewRequestDTO;
import com.greenatom.clientselfservice.domain.dto.review.ReviewResponseDTO;
import com.greenatom.clientselfservice.domain.entity.Review;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Review} and its DTO called {@link ReviewResponseDTO}.
 */

@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewResponseDTO, Review> {

    ReviewResponseDTO toDto(Review r);

    Review toEntity(ReviewResponseDTO r);

    Review toEntity(ReviewRequestDTO r);

    ReviewResponseDTO toResponse(ReviewRequestDTO r);
}
