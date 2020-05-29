package pl.lodz.p.it.zzpj.dogs.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DogService {

    Logger logger = LoggerFactory.getLogger(DogService.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final String randomByBreedBegin = "https://dog.ceo/api/breed/";
    private final String randomByBreedEnd = "/images/random";
    private final String randomDogURL = "https://dog.ceo/api/breeds/image/random";
    private final String breedListURL = "https://dog.ceo/api/breeds/list/all";
    private final String resourceUrl = "https://dog.ceo/api/breeds/image/random";

    public List<String> getBreedList() {
        ResponseEntity<String> response = restTemplate.getForEntity(breedListURL, String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        JSONObject breeds = (JSONObject) jsonObject.get("message");
        Set<String> entries = breeds.keySet();
        List<String> breedList = new ArrayList<>(entries);
        return breedList;
    }

    public String getRandomDogByBreed(String breed) {
        String dogUrl = "";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(randomByBreedBegin + breed + randomByBreedEnd, String.class);
            JSONObject jsonObject = parseResponseEntity(response);
            dogUrl = (String) jsonObject.get("message");
        }
        catch (HttpClientErrorException error){
            logger.error("Breed not found: " + breed);
        }
        return dogUrl;
    }

    public String getRandomDog() {
        ResponseEntity<String> response = restTemplate.getForEntity(randomDogURL, String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        String dogUrl = (String) jsonObject.get("message");
        return dogUrl;
    }

    private JSONObject parseResponseEntity(ResponseEntity<String> response) {
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
