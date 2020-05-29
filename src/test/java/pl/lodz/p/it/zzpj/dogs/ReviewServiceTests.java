package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.model.Review;
import pl.lodz.p.it.zzpj.dogs.services.ReviewService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class ReviewServiceTests {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void addReviewTest() {
        Review review = Review.builder()
                .url("url")
                .breed("breed")
                .rating(5)
                .username("username")
                .creationDate(LocalDateTime.now())
                .build();
        assertEquals(0, reviewService.getAllReviews().size());
        reviewService.addReview(review);
        assertEquals(1, reviewService.getAllReviews().size());
        assertDoesNotThrow(() -> reviewService.deleteReview("url", "username"));
        assertEquals(0, reviewService.getAllReviews().size());
    }

    @Test
    public void getAllReviewsTest() {
        assertEquals(0, reviewService.getAllReviews().size());
    }
}
