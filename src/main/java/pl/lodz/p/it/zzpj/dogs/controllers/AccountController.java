package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.dogs.dto.AccountDto;
import pl.lodz.p.it.zzpj.dogs.exceptions.AccountAlreadyExistsException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void register(@RequestBody AccountDto accountDto) throws AccountAlreadyExistsException {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .build();
        accountService.addAccount(account);
    }
}
