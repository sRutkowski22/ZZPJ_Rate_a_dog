package pl.lodz.p.it.zzpj.dogs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("reviews")
public @Data class Review {

    @Id
    private String id;
    private String url;
    private String breed;
    private int rating;
    private String username;
    private LocalDateTime creationDate;

    public Review() {
        this.id = UUID.randomUUID().toString();
    }
}
