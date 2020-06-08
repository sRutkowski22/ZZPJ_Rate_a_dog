package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.dogs.services.DogService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class DogServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DogService dogService = new DogService();
    

    @Test
    void getBreedListTest() {
        Mockito.when(restTemplate.getForEntity("https://dog.ceo/api/breeds/list/all", String.class))
                .thenReturn(new ResponseEntity("HERB", HttpStatus.OK));
        List<String> breedList = new ArrayList<>();
        assertEquals(0, breedList.size());
        breedList = dogService.getBreedList();
        assertNotEquals(0, breedList.size());
    }

    @Test
    void getRandomDogByBreedTest() {
        String url = dogService.getRandomDogByBreed("chihuahua");
        assertTrue(url.contains("chihuahua"));
    }

    @Test
    void getRandomDogTest() {
        String url = dogService.getRandomDog();
        assertNotEquals("", url);
    }
}
