package pl.lodz.p.it.zzpj.dogs.dto.mappers;

import pl.lodz.p.it.zzpj.dogs.dto.ReviewDto;
import pl.lodz.p.it.zzpj.dogs.model.Review;

public class ReviewMapper {
    public static ReviewDto mapToDto(Review review) {
        return ReviewDto.builder()
                .url(review.getUrl())
                .breed(review.getBreed())
                .rating(review.getRating())
                .username(review.getUsername())
                .creationDate(review.getCreationDate())
                .build();
    }

    public static Review mapFromDto(ReviewDto reviewDto) {
        return Review.builder()
                .url(reviewDto.getUrl())
                .breed(reviewDto.getBreed())
                .rating(reviewDto.getRating())
                .username(reviewDto.getUsername())
                .creationDate(reviewDto.getCreationDate())
                .build();
    }
}
