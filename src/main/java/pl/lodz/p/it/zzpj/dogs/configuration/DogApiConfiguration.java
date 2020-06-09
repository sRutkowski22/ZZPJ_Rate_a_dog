package pl.lodz.p.it.zzpj.dogs.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DogApiConfiguration {
    BREED_LIST_URL("https://dog.ceo/api/breeds/list/all"),
    RANDOM_BY_BREED_BEGIN("https://dog.ceo/api/breed/"),
    RANDOM_BY_BREED_END("/images/random"),
    RANDOM_DOG_URL("https://dog.ceo/api/breeds/image/random");

    @Getter
    private final String value;
}
