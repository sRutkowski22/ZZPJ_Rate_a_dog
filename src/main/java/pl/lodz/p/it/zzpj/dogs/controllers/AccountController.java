package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.dogs.dto.AccountDto;
import pl.lodz.p.it.zzpj.dogs.dto.DogDto;
import pl.lodz.p.it.zzpj.dogs.dto.mappers.AccountMapper;
import pl.lodz.p.it.zzpj.dogs.exceptions.AppBaseException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public void register(@RequestBody AccountDto accountDto) throws AppBaseException {
        accountService.addAccount(AccountMapper.mapFromDto(accountDto));
    }

    @GetMapping("/account/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public AccountDto getOwnAccount(@PathVariable String username) throws AppBaseException {
        return AccountMapper.mapToDto(accountService.getAccount(username));
    }

    @PutMapping("/account/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public void editOwnAccount(@PathVariable String username,
                               @RequestBody AccountDto accountDto) throws AppBaseException {
        accountService.editAccount(username, AccountMapper.mapFromDto(accountDto));
    }

    @GetMapping("all")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @PutMapping("/favorite/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public void addDogToFavorites(@PathVariable String username, @RequestBody DogDto dogDto) throws AppBaseException {
        String herb = dogDto.getUrl();
        accountService.addDogToFavorites(herb, username);
    }
}
