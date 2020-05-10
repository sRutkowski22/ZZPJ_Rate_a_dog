package pl.lodz.p.it.zzpj.dogs.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.dogs.exceptions.AccountAlreadyExistsException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void addAccount(Account account) throws AccountAlreadyExistsException {
        if (accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            accountRepository.insert(account);
        } else {
            throw new AccountAlreadyExistsException("Account already exists.");
        }
    }
}
