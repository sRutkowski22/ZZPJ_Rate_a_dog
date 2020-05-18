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
    private final String breedListURL = "https://dog.ceo/api/breeds/list/all";
    private final String resourceUrl = "https://dog.ceo/api/breeds/image/random";

    @GetMapping("/dog/breedlist")
    public List<String> getBreedList() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(breedListURL, String.class);
        JSONParser jp = new JSONParser();
        JSONObject jsonObject = null;
        try {
             jsonObject = (JSONObject) jp.parse(response.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject breeds = (JSONObject) jsonObject.get("message");
        Set<String> entries = breeds.keySet();
        List<String> breedList = new ArrayList<>(entries);
        return breedList;
    }
}
