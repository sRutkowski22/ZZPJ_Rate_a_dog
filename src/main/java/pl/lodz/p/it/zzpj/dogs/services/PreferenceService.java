package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
            log.info("Preference for:" + breed +" before: "+ preference);
            preference = calculatePreference(preference, rating);
            breedPreferences.put(breed,preference);
            account.setBreedPreferences(breedPreferences);
            log.info("After: " + preference);
            accountService.editAccount(username,account);
        } catch (AppBaseException e) {
            e.printStackTrace();
        }
    }

    private double calculatePreference(double preference, int rating) {
        double factor = getFactorByRating(rating);
        if(factor < 0){  //preference will be lowered
            return preference += preference * factor;
        }
        else {  //preference will be increased
            return preference += (2.0 - preference) * factor;
        }
    }

    private double getFactorByRating(int rating){
        double value = 0;
        switch (rating){
            case 1:
                value = -0.8;
                break;
            case 2:
                value = -0.5;
                break;
            case 3:
                value = -0.2;
                break;
            case 4:
                value = 0.4;
                break;
            case 5:
                value = 0.8;
                break;
        }
        return value;
    }
}
