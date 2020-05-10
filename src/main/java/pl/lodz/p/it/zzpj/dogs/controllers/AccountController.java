package pl.lodz.p.it.zzpj.dogs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.lodz.p.it.zzpj.dogs.dto.AccountDto;
import pl.lodz.p.it.zzpj.dogs.exceptions.AccountAlreadyExistsException;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.services.AccountService;

@Controller
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "accountDto", new AccountDto());
    }

    @PostMapping("/register")
    public String register(@ModelAttribute AccountDto accountDto) throws AccountAlreadyExistsException {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .build();
        accountService.addAccount(account);
        return "greeting";
    }
}
