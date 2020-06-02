package pl.lodz.p.it.zzpj.dogs.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {
    private String url;
    private String breed;
    private int rating;
    private String username;
    private String creationDate;
}
