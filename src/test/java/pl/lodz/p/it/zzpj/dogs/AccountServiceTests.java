package pl.lodz.p.it.zzpj.dogs;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Import(TestMongoConfiguration.class)
@ExtendWith(TestSuiteExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTests {

    @Autowired
    AccountService accountService;

    @Test
    @Order(1)
    void getAllTest() {
        int size = accountService.getAll().size();
        assertEquals(0, size);
    }

    @Test
    @Order(2)
    void addAccountTest() throws AppBaseException {
        int size = accountService.getAll().size();
        accountService.addAccount(Account.builder().username("addTest").password("password123").firstName("addTest").build());
        int size2 = accountService.getAll().size();
        assertEquals(size + 1, size2);
    }

    @Test
    @Order(3)
    void getAccountTest() throws AppBaseException {
        accountService.addAccount(Account.builder().username("getAccountTest").password("password123").firstName("getAccountTest").build());
        String name = accountService.getAccount("getAccountTest").getFirstName();
        assertEquals("getAccountTest", name);
    }

    @Test
    @Order(4)
    void editAccountTest() throws AppBaseException {
        Account account = Account.builder().username("editAccountTest").password("password123").firstName("editAccountTest").build();
        accountService.addAccount(account);
        String name = accountService.getAccount("editAccountTest").getFirstName();
        account.setFirstName("edited");
        accountService.editAccount(account.getUsername(), account);
        String editedName = accountService.getAccount("editAccountTest").getFirstName();
        assertNotEquals(name, editedName);
        assertEquals("edited", editedName);
    }

    @Test
    @Order(5)
    void addDogToFavoritesTest() throws AppBaseException {
        Account account = Account.builder().username("favorite").password("password123").firstName("favorite").build();
        accountService.addAccount(account);
        int favoritesAmount1 = accountService.getAccount(account.getUsername()).getFavoriteDogs().size();
        accountService.addDogToFavorites("url", account.getUsername());
        int favoritesAmount2 = accountService.getAccount(account.getUsername()).getFavoriteDogs().size();
        assertEquals(favoritesAmount1 + 1, favoritesAmount2);
    }

    @Test
    @Order(6)
    void getFavoriteDogsTest() throws AppBaseException {
        Account account = Account.builder().username("favorites").password("password123").firstName("favorites").build();
        accountService.addAccount(account);
        accountService.addDogToFavorites("favoritesUrl", account.getUsername());
        int favoritesAmount = accountService.getAccount(account.getUsername()).getFavoriteDogs().size();
        assertEquals(1, favoritesAmount);
    }
}
