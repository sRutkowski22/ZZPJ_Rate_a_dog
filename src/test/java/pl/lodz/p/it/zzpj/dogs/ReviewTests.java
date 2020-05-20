package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.model.Review;
import pl.lodz.p.it.zzpj.dogs.repositories.ReviewRepository;
import pl.lodz.p.it.zzpj.dogs.services.ReviewService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class ReviewTests {

    private ReviewRepository repository;
    private ReviewService reviewService;
    private List<Review> reviews;

    @BeforeEach
    void prepare() {
        reviews = new ArrayList<>();
        repository = mock(ReviewRepository.class);

        reviews.add(Review.builder().url("url").breed("breed").username("username").rating(5).creationDate(LocalDateTime.now()).build());
        reviews.add(Review.builder().url("url2").breed("breed2").username("username2").rating(3).creationDate(LocalDateTime.now()).build());
        reviews.add(Review.builder().url("url3").breed("breed3").username("username3").rating(1).creationDate(LocalDateTime.now()).build());

        reviewService = new ReviewService(repository);
    }

    @AfterEach
    void clean() {
        reviews = new ArrayList<>();
    }

    @Test
    void getAllReviewsTest() {
        when(repository.findAll()).thenReturn(reviews);

        List<Review> reviewsList = reviewService.getAllReviews();

        assertThat(reviewsList.size()).isEqualTo(3);
    }
}
