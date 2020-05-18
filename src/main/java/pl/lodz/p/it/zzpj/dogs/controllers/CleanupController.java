package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;
import pl.lodz.p.it.zzpj.dogs.repositories.DogRepository;

@RestController
@AllArgsConstructor
public class CleanupController {

    private final AccountRepository accountRepository;
    private final DogRepository dogRepository;

    @DeleteMapping("/clean")
    public void cleanDatabase() {
        accountRepository.deleteAll();
        dogRepository.deleteAll();
    }
}
