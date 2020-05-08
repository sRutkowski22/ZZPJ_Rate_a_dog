package pl.lodz.p.it.zzpj.dogs;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.dogs.model.Account;
import pl.lodz.p.it.zzpj.dogs.repositories.AccountRepository;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class MongoUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if (accountRepository.findByLogin(login).isPresent()) {
            Account account = accountRepository.findByLogin(login).get();
            Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
            return new User(account.getLogin(), account.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }
}
