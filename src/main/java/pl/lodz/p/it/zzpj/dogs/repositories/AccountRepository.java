package pl.lodz.p.it.zzpj.dogs.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.zzpj.dogs.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {


}
