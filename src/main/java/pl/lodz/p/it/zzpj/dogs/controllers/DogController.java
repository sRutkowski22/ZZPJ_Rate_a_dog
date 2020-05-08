package pl.lodz.p.it.zzpj.dogs.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.dogs.model.Dog;
import pl.lodz.p.it.zzpj.dogs.repositories.DogRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DogController {

    private final DogRepository dogRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String resourceUrl = "https://dog.ceo/api/breeds/image/random";

    @GetMapping("/dog")
    public String addDog(Model model) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        log.error(response.toString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode message = root.path("message");
        String imageUrl = message.toString().replace("\"", "");
        Dog dog = new Dog();
        dog.setUrl(imageUrl);
        dog.setBreed(imageUrl.substring(30, imageUrl.lastIndexOf("/")));
        dogRepository.insert(dog);
        model.addAttribute("image", dog.getUrl());
        return "greeting";
    }

    @GetMapping("/dog/{breed}")
     public String addDogByBreed(Model model, @PathVariable String breed) throws JsonProcessingException {

        log.error(String.format("https://dog.ceo/api/breed/%s/images/random",breed));
        ResponseEntity<String> response = restTemplate.getForEntity(
                String.format("https://dog.ceo/api/breed/%s/images/random",breed),String.class);

        log.error(response.toString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        root = mapper.readTree(response.getBody());
        JsonNode message = root.path("message");
        String imageUrl = message.toString().replace("\"", "");
        Dog dog = new Dog();
        dog.setUrl(imageUrl);
        dog.setBreed(imageUrl.substring(30, imageUrl.lastIndexOf("/")));
        dogRepository.insert(dog);
        model.addAttribute("imageByBreed", dog.getUrl());
        return "greeting";
    }
}
