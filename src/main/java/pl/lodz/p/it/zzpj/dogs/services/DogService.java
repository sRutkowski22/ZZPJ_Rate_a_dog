package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Preference;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class DogService {


    private AccountService accountService;
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
            log.error("Breed not found: " + breed);
        }
        return dogUrl;
    }

    public String getRandomDog(String user) {
//        ResponseEntity<String> response = restTemplate.getForEntity(randomDogURL, String.class);
        Map<String,Double> breedPreferences = new HashMap<>();
        List <Preference> preferences = new ArrayList<>();
        try {
            breedPreferences = accountService.getBreedPreferences(user);
            for (Map.Entry<String, Double> entry : breedPreferences.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                log.error(entry.getKey() + entry.getValue());
                preferences.add(new Preference(entry.getKey(),entry.getValue()));
            }
        } catch (AppBaseException e) {
            e.printStackTrace();
        }
        double ammount = 0;
        for(Preference preference: preferences)
        {
            ammount += preference.getPreferenceValue();
        }
        Random r = new Random();
        double randomValue = ammount * r.nextDouble();
        log.error(Double.toString(randomValue));
        double border = 0;
        String breed = "";
        for(Preference preference: preferences)
        {
            border+=preference.getPreferenceValue();
            if(border>randomValue){
                breed = preference.getBreed();
                break;
            }
        }
        ResponseEntity<String> response = restTemplate.getForEntity(randomByBreedBegin+ breed +randomByBreedEnd, String.class);
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
