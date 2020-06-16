package pl.lodz.p.it.zzpj.dogs.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum PreferencesFactorsConfiguration {
    RATING_1(1,-0.8),
    RATING_2(2,-0.5),
    RATING_3(3,-0.2),
    RATING_4(4,0.4),
    RATING_5(5,0.8);

    @Getter
    private final Integer key;
    @Getter
    private final Double value;

    private static final Map<Integer,Double> BY_KEY = new HashMap<>();
    static {
        for (PreferencesFactorsConfiguration pfe: values()){
            BY_KEY.put(pfe.key,pfe.value);
        }
    }

    public static Double valueByKey(Integer key){
        return BY_KEY.get(key);
    }
}
