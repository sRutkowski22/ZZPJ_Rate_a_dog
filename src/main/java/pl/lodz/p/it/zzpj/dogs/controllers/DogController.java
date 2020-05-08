package pl.lodz.p.it.zzpj.dogs.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
public class DogController {

    private RestTemplate restTemplate = new RestTemplate();
    private String resourceUrl = "https://dog.ceo/api/breeds/image/random";

    @GetMapping("/dog")
    public String addDog(Model model) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode imageUrl = root.path("message");
        model.addAttribute("image", imageUrl.toString().replace("\"", ""));
        return "greeting";
    }
}
