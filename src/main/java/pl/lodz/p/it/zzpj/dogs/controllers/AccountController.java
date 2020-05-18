package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.dogs.dto.AccountDto;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void register(@RequestBody AccountDto accountDto) throws AppBaseException {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .build();
        accountService.addAccount(account);
    }

    @GetMapping("/account/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public AccountDto getOwnAccount(@PathVariable String username) throws AppBaseException {
        Account account = accountService.getAccount(username);
        return AccountDto.builder()
                .username(account.getUsername())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .build();
    }

    @PutMapping("/account/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public void editOwnAccount(@PathVariable String username,
                               @RequestBody AccountDto accountDto) throws AppBaseException {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .build();
        accountService.editAccount(username, account);
    }

    @GetMapping("all")
    public List<Account> getAll() {
        return accountService.getAll();
    }
}
