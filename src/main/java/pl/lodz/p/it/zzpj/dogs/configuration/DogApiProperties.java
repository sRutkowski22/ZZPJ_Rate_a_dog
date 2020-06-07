package pl.lodz.p.it.zzpj.dogs.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "dogs")
@EnableConfigurationProperties(DogApiProperties.class)
public @Data class DogApiProperties {

    private String randomByBreedBegin;
    private String randomByBreedEnd;
    private String randomDogUrl;
    private String breedListUrl;
}
