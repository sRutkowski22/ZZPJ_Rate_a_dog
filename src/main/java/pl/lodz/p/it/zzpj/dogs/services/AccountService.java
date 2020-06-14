package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.dogs.exceptions.AccountException;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void addAccount(Account account) throws AppBaseException {
        if (accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.insert(account);
        } else {
            throw new AccountException("Account already exists.");
        }
    }

    public Account getAccount(String username) throws AppBaseException {
        if (accountRepository.findByUsername(username).isPresent()) {
            return accountRepository.findByUsername(username).get();
        } else {
            throw new AccountException("Account not found.");
        }
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void editAccount(String username, Account account) throws AppBaseException {
        if (accountRepository.findByUsername(username).isPresent()) {
            Account temp = accountRepository.findByUsername(username).get();
            if (username.equals(account.getUsername())) {
                if (account.getPassword() != null) {
                    temp.setPassword(passwordEncoder.encode(account.getPassword()));
                }
                temp.setFirstName(account.getFirstName());
                temp.setLastName(account.getLastName());
                temp.setBreedPreferences(account.getBreedPreferences());
                accountRepository.save(temp);
            } else {
                throw new AccountException("Usernames do not match.");
            }
        } else {
            throw new AccountException("Account not found.");
        }
    }

    public void addDogToFavorites(String url, String username) throws AccountException {
        if (accountRepository.findByUsername(username).isPresent()) {
            Account temp = accountRepository.findByUsername(username).get();
            temp.getFavoriteDogs().add(url);
            accountRepository.save(temp);
        } else {
            throw new AccountException("Account not found.");
        }
    }

    public List<String> getFavoriteDogs(String username) throws AccountException {
        if (accountRepository.findByUsername(username).isPresent()) {
            return accountRepository.findByUsername(username).get().getFavoriteDogs();
        } else {
            throw new AccountException("Account not found");
        }
    }

    public Map<String,Double> getBreedPreferences(String username) throws AppBaseException {
        if (accountRepository.findByUsername(username).isPresent()) {
            return accountRepository.findByUsername(username).get().getBreedPreferences();
        } else {
            throw new AccountException("Account not found");
        }
    }
}
