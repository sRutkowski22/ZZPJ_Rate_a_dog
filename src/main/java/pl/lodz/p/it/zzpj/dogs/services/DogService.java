package pl.lodz.p.it.zzpj.dogs.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import org.json.simple.JSONObject;

@Service
@AllArgsConstructor
public class DogService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String randomByBreedBegin = "https://dog.ceo/api/breed/";
    private final String randomByBreedEnd = "/images/random";
    private final String randomDogURL = "https://dog.ceo/api/breeds/image/random";
    private final String breedListURL = "https://dog.ceo/api/breeds/list/all";
    private final String resourceUrl = "https://dog.ceo/api/breeds/image/random";

    public List<String> getBreedList() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(breedListURL, String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        JSONObject breeds = (JSONObject) jsonObject.get("message");
        Set<String> entries = breeds.keySet();
        List<String> breedList = new ArrayList<>(entries);
        return breedList;
    }

    public String getRandomDogByBreed(String breed) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(randomByBreedBegin + breed + randomByBreedEnd, String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        String dogUrl = (String) jsonObject.get("message");
        return dogUrl;
    }

    public String getRandomDog() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(randomDogURL, String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        String dogUrl = (String) jsonObject.get("message");
        return dogUrl;
    }

    private JSONObject parseResponseEntity(ResponseEntity<String> response){
        JSONParser jp = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jp.parse(response.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
