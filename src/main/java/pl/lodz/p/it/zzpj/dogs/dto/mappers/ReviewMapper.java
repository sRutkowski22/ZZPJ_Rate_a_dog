package pl.lodz.p.it.zzpj.dogs.dto.mappers;

import pl.lodz.p.it.zzpj.dogs.dto.ReviewDto;
import pl.lodz.p.it.zzpj.dogs.model.Review;

import java.time.LocalDateTime;

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
                .breed(reviewDto.getUrl().substring(30, reviewDto.getUrl().lastIndexOf("/")))
                .rating(reviewDto.getRating())
                .username(reviewDto.getUsername())
                .creationDate(LocalDateTime.now())
                .build();
    }
}
