package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.services.ReviewService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class ReviewServiceTests {

    @Autowired
    private ReviewService reviewService;

    @Test
    void getAllReviewsTest() {
        assertEquals(0, reviewService.getAllReviews().size());
    }
}
