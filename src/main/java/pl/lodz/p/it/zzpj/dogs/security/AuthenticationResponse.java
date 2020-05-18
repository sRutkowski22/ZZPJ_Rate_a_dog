package pl.lodz.p.it.zzpj.dogs.security;

import lombok.Data;

import java.io.Serializable;

public @Data class AuthenticationResponse implements Serializable {

    private final String jwt;
}
