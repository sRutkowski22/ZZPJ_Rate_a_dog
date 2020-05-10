package pl.lodz.p.it.zzpj.dogs.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lodz.p.it.zzpj.dogs.services.MongoUserDetailsService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MongoUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register.html").permitAll();
//                .antMatchers("/**").hasRole("USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
