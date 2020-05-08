package pl.lodz.p.it.zzpj.dogs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("dogs")
public @Data class Dog {

    @Id
    private String id;
    private String url;
    private String breed;

    public Dog() {
        this.id = UUID.randomUUID().toString();
    }
}
