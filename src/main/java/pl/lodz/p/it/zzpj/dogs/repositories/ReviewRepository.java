package pl.lodz.p.it.zzpj.dogs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.zzpj.dogs.model.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findAllByUrl(String url);
    List<Review> findAllByUsername(String username);
    List<Review> findAllByBreed(String breed);
    List<Review> findAllByCreationDateBetween(LocalDateTime firstDate, LocalDateTime secondDate);
    Optional<Review> findByUrlAndUsername(String url, String username);
}
