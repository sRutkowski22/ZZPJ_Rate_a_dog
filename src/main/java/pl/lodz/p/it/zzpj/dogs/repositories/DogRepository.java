package pl.lodz.p.it.zzpj.dogs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.zzpj.dogs.model.Dog;

import java.util.UUID;

@Repository
public interface DogRepository extends MongoRepository<Dog, UUID> {

}
