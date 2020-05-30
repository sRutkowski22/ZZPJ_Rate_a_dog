package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.exceptions.ReviewException;
import pl.lodz.p.it.zzpj.dogs.model.Review;
import pl.lodz.p.it.zzpj.dogs.services.ReviewService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewServiceTests {

    @Autowired
    private ReviewService reviewService;

    @Test
    @Order(1)
    void getAllReviewsTest() {
        assertEquals(0, reviewService.getAllReviews().size());
    }

    @Test
    @Order(2)
    void addReviewTest() {
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
    @Order(3)
    void getReviewTest() throws ReviewException {
        Review review = Review.builder()
                .url("urlreview")
                .breed("breed")
                .rating(5)
                .username("username")
                .creationDate(LocalDateTime.now())
                .build();
        reviewService.addReview(review);

        Review newReview = reviewService.getReview("urlreview", "username");
        assertEquals(5, newReview.getRating());
        assertEquals("username", newReview.getUsername());
    }

    @Test
    @Order(4)
    void getReviewsForUser() {
        Review review1 = Review.builder().url("urluser1").breed("breed").rating(5).username("username1").creationDate(LocalDateTime.now()).build();
        Review review2 = Review.builder().url("urluser2").breed("breed").rating(5).username("username2").creationDate(LocalDateTime.now()).build();
        Review review3 = Review.builder().url("urluser3").breed("breed").rating(5).username("username1").creationDate(LocalDateTime.now()).build();
        reviewService.addReview(review1);
        reviewService.addReview(review2);
        reviewService.addReview(review3);
        assertEquals(2, reviewService.getAllReviewsForUser("username1").size());
    }

    @Test
    @Order(5)
    void getReviewsForBreed() {
        Review review1 = Review.builder().url("url").breed("test").rating(5).username("usernamebreed1").creationDate(LocalDateTime.now()).build();
        Review review2 = Review.builder().url("url2").breed("test").rating(5).username("usernamebreed2").creationDate(LocalDateTime.now()).build();
        reviewService.addReview(review1);
        reviewService.addReview(review2);
        assertEquals(2, reviewService.getAllReviewsForBreed("test").size());
    }

    @Test
    @Order(6)
    void getReviewsForUrl() {
        Review review1 = Review.builder().url("urltest").breed("urltest").rating(5).username("usernameurl").creationDate(LocalDateTime.now()).build();
        reviewService.addReview(review1);
        assertEquals(1, reviewService.getAllReviewsForUrl("urltest").size());
    }

    @Test
    @Order(7)
    void getReviewsBetweenDate() {
        assertEquals(0, reviewService.getAllReviewsBetweenDate(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1)).size());
    }
}
