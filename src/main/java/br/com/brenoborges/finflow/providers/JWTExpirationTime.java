package br.com.brenoborges.finflow.providers;

import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
public class JWTExpirationTime {

    private int expirationTimeLoginInMinutes = 60;
    private int expirationTimeResetPasswordInMinutes = 5;

}
