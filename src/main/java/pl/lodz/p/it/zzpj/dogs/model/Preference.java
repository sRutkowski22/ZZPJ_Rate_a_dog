package pl.lodz.p.it.zzpj.dogs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class Preference {

    private String breed;
    private double preferenceValue;
}
