package pl.lodz.p.it.zzpj.dogs.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;
import pl.lodz.p.it.zzpj.dogs.services.DogService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DogController {
    private final DogService dogService;
    @GetMapping("/dog/breedlist")
    public List<String> getBreedList() throws JsonProcessingException {
        return dogService.getBreedList();
    }

    @GetMapping("/dog/breed/random/{breed}")
    public String getRandomDogByBreed(@PathVariable String breed) throws JsonProcessingException {
        return dogService.getRandomDogByBreed(breed);
    }

    @GetMapping("/dog/random")
    public String getRandomDog() throws JsonProcessingException {
        return dogService.getRandomDog();
    }
}
