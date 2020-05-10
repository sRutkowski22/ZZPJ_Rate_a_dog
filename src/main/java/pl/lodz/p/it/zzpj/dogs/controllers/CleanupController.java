package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.dogs.repositories.DogRepository;

@CrossOrigin
@RestController
@AllArgsConstructor
public class CleanupController {

    private final DogRepository dogRepository;

    @DeleteMapping("/clean")
    public void cleanDatabase() {
        dogRepository.deleteAll();
    }
}