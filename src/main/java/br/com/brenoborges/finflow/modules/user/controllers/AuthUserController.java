package br.com.brenoborges.finflow.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.user.dtos.LoginRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.LoginResponseDTO;
import br.com.brenoborges.finflow.modules.user.useCases.AuthTokenUseCase;

@RestController
@RequestMapping("/user")
public class AuthUserController {

    @Autowired
    private AuthTokenUseCase authTokenUseCase;

    @PostMapping("/login")
    public ResponseEntity<Object> auth(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO token = this.authTokenUseCase.loginToken(loginRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
