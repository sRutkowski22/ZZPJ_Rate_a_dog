package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.dogs.exceptions.ReviewException;
import pl.lodz.p.it.zzpj.dogs.model.Review;
import pl.lodz.p.it.zzpj.dogs.repositories.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PreferenceService preferenceService;

    public void addReview(Review review) {
        if(reviewRepository.findByUrlAndUsername(review.getUrl(), review.getUsername()).isPresent()) {
            Review tmpReview = reviewRepository.findByUrlAndUsername(review.getUrl(), review.getUsername()).get();
            tmpReview.setRating(review.getRating());
            tmpReview.setCreationDate(review.getCreationDate());
            reviewRepository.save(tmpReview);
        } else reviewRepository.insert(review);

        preferenceService.adjustPreference(review.getUsername(),review.getBreed(),review.getRating());
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> filterReviews(String filter) {
        return reviewRepository.findAll().stream()
                .filter(review -> review.getBreed().toLowerCase().contains(filter.toLowerCase())
                        || review.getUsername().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Review getReview(String url, String username) throws ReviewException {
        if(reviewRepository.findByUrlAndUsername(url, username).isPresent())
            return reviewRepository.findByUrlAndUsername(url, username).get();
        else throw new ReviewException("Review does not exist");
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

    public void deleteReview(String url, String username) throws ReviewException {
        if(reviewRepository.findByUrlAndUsername(url, username).isPresent()) {
            reviewRepository.deleteByUrlAndUsername(url, username);
        } else {
            throw new ReviewException("Review does not exist");
        }
    }
}
