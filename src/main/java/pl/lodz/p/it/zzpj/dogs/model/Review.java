package pl.lodz.p.it.zzpj.dogs.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document("reviews")
@Builder
public @Data class Review {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String url;
    private String breed;
    private int rating;
    private String username;
    private LocalDateTime creationDate;
}
