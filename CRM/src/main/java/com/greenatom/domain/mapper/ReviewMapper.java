package com.greenatom.domain.mapper;


import com.greenatom.domain.dto.review.ReviewRequestDTO;
import com.greenatom.domain.dto.review.ReviewResponseDTO;
import com.greenatom.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Review} and its DTO called {@link ReviewResponseDTO}.
 */

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ClientMapper.class})
public interface ReviewMapper extends EntityMapper<ReviewResponseDTO, Review> {

    @Mapping(target = "clientId", source = "r.client.id")
    @Mapping(target = "orderItemId", source = "r.orderItem.id")
    ReviewResponseDTO toDto(Review r);

    Review toEntity(ReviewResponseDTO r);

    Review toEntity(ReviewRequestDTO r);

    ReviewResponseDTO toResponse(ReviewRequestDTO r);
}
