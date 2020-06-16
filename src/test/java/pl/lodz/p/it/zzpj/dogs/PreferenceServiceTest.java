package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;
import pl.lodz.p.it.zzpj.dogs.services.PreferenceService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
public class PreferenceServiceTest {

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    private void cleanDatabase() {
        accountRepository.deleteAll();
    }
    
}

