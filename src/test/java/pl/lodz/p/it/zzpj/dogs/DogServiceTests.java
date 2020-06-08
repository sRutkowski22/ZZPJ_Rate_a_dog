package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import pl.lodz.p.it.zzpj.dogs.services.DogService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = {
        "breed-list-url = https://dog.ceo/api/breeds/list/all",
        "random-by-breed-begin = https://dog.ceo/api/breed/",
        "random-by-breed-end = /images/random",
        "random-dog-url = https://dog.ceo/api/breeds/image/random"})
@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class DogServiceTests {

    @Autowired
    private DogService dogService;

    @Test
    void getBreedListTest() {
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
