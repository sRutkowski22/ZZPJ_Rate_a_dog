package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.dogs.services.DogService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
public class DogController {

    private final DogService dogService;

    @GetMapping("/dog/breedlist")
    public List<String> getBreedList() {
        return dogService.getBreedList();
    }

    @GetMapping("/dog/breed/random/{breed}")
    public String getRandomDogByBreed(@PathVariable String breed) {
        return dogService.getRandomDogByBreed(breed);
    }

    @GetMapping("/dog/random")
    public String getRandomDog() {
        return dogService.getRandomDog();
    }
}
