package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.dogs.configuration.PreferencesFactorsConfiguration;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class PreferenceService {

    private final AccountService accountService;

    public void adjustPreference(String username, String breed, int rating){
        try {
            Account account = accountService.getAccount(username);
            Map<String, Double> breedPreferences = account.getBreedPreferences();
            Double preference = breedPreferences.getOrDefault(breed, 1.0); //Sets preference to 1.0 if breed wasn't in the map
            preference = calculatePreference(preference, rating);
            breedPreferences.put(breed,preference);
            account.setBreedPreferences(breedPreferences);
            accountService.editAccount(username,account);
        } catch (AppBaseException e) {
            e.printStackTrace();
        }
    }

    private double calculatePreference(double preference, int rating) {
        double factor = getFactorByRating(rating);
        if(factor < 0){  //preference will be lowered
            return preference + Math.max(preference * factor, factor);
        }
        else {  //preference will be increased
            return preference + Math.min((2.0 - preference) * factor, factor);
        }
    }

    private double getFactorByRating(int rating){ //Hardcoded cause properties is skopcone
        switch (rating){
            case 1:
                return PreferencesFactorsConfiguration.RATING_1.getValue();
            case 2:
                return PreferencesFactorsConfiguration.RATING_2.getValue();
            case 3:
                return PreferencesFactorsConfiguration.RATING_3.getValue();
            case 4:
                return PreferencesFactorsConfiguration.RATING_4.getValue();
            case 5:
                return PreferencesFactorsConfiguration.RATING_5.getValue();
        }
        return 0.0;
    }
}
