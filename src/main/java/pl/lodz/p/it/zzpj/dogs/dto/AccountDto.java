package pl.lodz.p.it.zzpj.dogs.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public @Data class AccountDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
