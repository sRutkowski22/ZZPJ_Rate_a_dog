package pl.lodz.p.it.zzpj.dogs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.configuration.PreferencesFactorsConfiguration;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;
import pl.lodz.p.it.zzpj.dogs.services.PreferenceService;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class PreferenceServiceTest {

    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Before
    private void init() throws AppBaseException {
        accountRepository.deleteAll();
    }

    @Test
    void valueByRatingTest(){
        double factor = preferenceService.getFactorByRating(1);
        double expectedFactor = PreferencesFactorsConfiguration.RATING_1.getValue();
        Assert.assertEquals(expectedFactor, factor, 0.0);
    }

    @Test
    void valueByRatingDefaultValueTest(){
        double factor = preferenceService.getFactorByRating(20000);
        double expectedFactor = PreferencesFactorsConfiguration.DEFAULT.getValue();
        Assert.assertEquals(expectedFactor, factor, 0.0);
    }

    @Test
    void adjustPreferenceTest() throws AppBaseException {
        Account account = Account.builder().username("preftest").password("password123").firstName("preftest").build();
        accountService.addAccount(account);
        preferenceService.adjustPreference("preftest","shiba",5);
        double expectedValue = 2.0 + PreferencesFactorsConfiguration.RATING_5.getValue();
        double actualValue = accountService
                            .getAccount("preftest")
                            .getBreedPreferences()
                            .get("shiba");
        Assert.assertEquals(expectedValue, actualValue, 0.0);
    }
}

