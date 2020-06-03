package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.dogs.dto.DogDto;
import pl.lodz.p.it.zzpj.dogs.dto.ReviewDto;
import pl.lodz.p.it.zzpj.dogs.dto.mappers.ReviewMapper;
import pl.lodz.p.it.zzpj.dogs.exceptions.ReviewException;
import pl.lodz.p.it.zzpj.dogs.services.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public void addReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(ReviewMapper.mapFromDto(reviewDto));
    }

    @GetMapping("/reviews")
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews().stream().map(ReviewMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/review/{url}/{username}")
    public ReviewDto getReview(@PathVariable String url, @PathVariable String username) throws ReviewException {
        return ReviewMapper.mapToDto(reviewService.getReview(url, username));
    }

    @GetMapping("/reviews/user/{username}")
    public List<ReviewDto> getReviewsForUser(@PathVariable String username) {
        return reviewService.getAllReviewsForUser(username).stream().map(ReviewMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/reviews/breed/{breed}")
    public List<ReviewDto> getAllReviewsForBreed(@PathVariable String breed) {
        return reviewService.getAllReviewsForBreed(breed).stream().map(ReviewMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/reviews/url/{url}")
    public List<ReviewDto> getAllReviewsForUrl(@PathVariable String url) {
        return reviewService.getAllReviewsForUrl(url).stream().map(ReviewMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/reviews/date/{firstDate}/{secondDate}")
    public List<ReviewDto> getAllReviewsBetweenDate(@PathVariable LocalDateTime firstDate, @PathVariable LocalDateTime secondDate) {
        return reviewService.getAllReviewsBetweenDate(firstDate, secondDate).stream().map(ReviewMapper::mapToDto).collect(Collectors.toList());
    }

    @PostMapping("/reviews/average")
    public int getAverageRating(@RequestBody DogDto dogDto) {
        List<ReviewDto> reviews = reviewService.getAllReviewsForUrl(dogDto.getUrl()).stream().map(ReviewMapper::mapToDto).collect(Collectors.toList());
        int rating = 0;
        for(ReviewDto reviewDto : reviews)
            rating += reviewDto.getRating();
        return rating/reviews.size();
    }
}
