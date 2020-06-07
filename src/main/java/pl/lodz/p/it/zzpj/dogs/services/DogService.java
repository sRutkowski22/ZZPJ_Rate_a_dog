package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Preference;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class DogService {

    private final AccountService accountService;
    private final RestTemplate restTemplate;
    private final Environment env;

    public List<String> getBreedList() {
        ResponseEntity<String> response = restTemplate.getForEntity(getProperty("breed-list-url"), String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        JSONObject breeds = (JSONObject) jsonObject.get("message");
        Set<String> entries = breeds.keySet();
        return new ArrayList<>(entries);
    }

    public String getRandomDogByBreed(String breed) {
        String dogUrl = "";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(getProperty("random-by-breed-begin") + breed + getProperty("random-by-breed-end"), String.class);
            JSONObject jsonObject = parseResponseEntity(response);
            dogUrl = (String) jsonObject.get("message");
        }
        catch (HttpClientErrorException error) {
            log.error("Breed not found: " + breed);
        }
        return dogUrl;
    }

    public String getRandomDog() {
        ResponseEntity<String> response = restTemplate.getForEntity(getProperty("random-dog-url"), String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        return (String) jsonObject.get("message");
    }

    public String getRandomDog(String user) throws AppBaseException {
        List <Preference> preferences = new ArrayList<>();
        Map<String, Double> breedPreferences = accountService.getBreedPreferences(user);
        for (Map.Entry<String, Double> entry : breedPreferences.entrySet()) {
            preferences.add(new Preference(entry.getKey(), entry.getValue()));
        }

        double amount = 0;
        for (Preference preference : preferences) {
            amount += preference.getPreferenceValue();
        }
        Random r = new Random();
        double randomValue = amount * r.nextDouble();
        double border = 0;
        String breed = "";
        for(Preference preference: preferences)
        {
            border += preference.getPreferenceValue();
            if (border > randomValue) {
                breed = preference.getBreed();
                break;
            }
        }

        ResponseEntity<String> response = restTemplate.getForEntity(getProperty("random-by-breed-begin") + breed + getProperty("random-by-breed-end"), String.class);
        JSONObject jsonObject = parseResponseEntity(response);
        return (String) jsonObject.get("message");
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

    private String getProperty(String key) {
        return Objects.requireNonNull(env.getProperty(key));
    }
}
