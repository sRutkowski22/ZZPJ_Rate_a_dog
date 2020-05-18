package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.dogs.exceptions.ReviewException;
import pl.lodz.p.it.zzpj.dogs.model.Review;
import pl.lodz.p.it.zzpj.dogs.repositories.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void addReview(Review review) {
        reviewRepository.insert(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReview(String url, String username) throws ReviewException {
        if(reviewRepository.findByUrlAndUsername(url, username).isPresent())
            return reviewRepository.findByUrlAndUsername(url, username).get();
        else throw new ReviewException("Review does not exists");
    }

    public List<Review> getAllReviewsForUser(String username) {
        return reviewRepository.findAllByUsername(username);
    }

    public List<Review> getAllReviewsForBreed(String breed) {
        return reviewRepository.findAllByBreed(breed);
    }

    public List<Review> getAllReviewsForUrl(String url) {
        return reviewRepository.findAllByUrl(url);
    }

    public List<Review> getAllReviewsBetweenDate(LocalDateTime firstDate, LocalDateTime secondDate) {
        return reviewRepository.findAllByCreationDateBetween(firstDate, secondDate);
    }
}
